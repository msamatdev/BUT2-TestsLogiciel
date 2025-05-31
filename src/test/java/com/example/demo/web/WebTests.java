package com.example.demo.web;

import com.example.demo.data.Voiture;
import com.example.demo.service.Echantillon;
import com.example.demo.service.StatistiqueImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testExceptionPasDeVoiture() throws Exception {
        when(statistiqueImpl.prixMoyen()).thenThrow(new ArithmeticException());

        mockMvc.perform(get("/statistique"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreerVoiture() throws Exception {
        String voitureJson = "{\"marque\":\"Nissan\",\"prix\":12000}";

        mockMvc.perform(post("/voiture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voitureJson))
                .andExpect(status().isOk());

        verify(statistiqueImpl, times(1)).ajouter(argThat(voiture ->
                Objects.equals(voiture.getMarque(), "Nissan") &&
                voiture.getPrix() == 12000
        ));
    }

    @Test
    void testPrixMoyen() throws Exception {
        when(statistiqueImpl.prixMoyen()).thenReturn(new Echantillon(2, 11000));

        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marque\":\"Nissan\", \"prix\":10000}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marque\":\"Mazda\", \"prix\":12000}"))
                .andExpect(status().isOk());

        // Now call GET /statistique and expect the Echantillon result
        mockMvc.perform(get("/statistique"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreDeVoitures").value(2))
                .andExpect(jsonPath("$.prixMoyen").value(11000));
    }
}

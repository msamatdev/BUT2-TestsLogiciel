package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;


@SpringBootTest
public class StatistiqueTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Test
    public void testException()
    {
        doThrow(ArithmeticException.class).when(statistiqueImpl).prixMoyen();
    }

    @Test
    public void testAjout()
    {
        Voiture voiture = new Voiture("test", 1234);

        doNothing().when(statistiqueImpl).ajouter(voiture);
    }

    @Test
    public void testPrixMoyen()
    {
        for (int i = 0; i < 4; i++)
        {
            Voiture voiture = new Voiture("test", 5);
            doNothing().when(statistiqueImpl).ajouter(voiture);
        }

        when(statistiqueImpl.prixMoyen()).thenReturn(new Echantillon(4, 5));
    }
}

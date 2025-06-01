package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



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
        Voiture v = mock(Voiture.class);

        for (int i = 0; i < 3; i++) {
            statistiqueImpl.ajouter(v);
        }

        verify(statistiqueImpl, times(3)).ajouter(v);
    }

    @Test
    public void testPrixMoyen()
    {
        StatistiqueImpl s = new StatistiqueImpl();

        Voiture v1 = mock(Voiture.class);
        when(v1.getPrix()).thenReturn(1234);
        s.ajouter(v1);

        Voiture v2 = mock(Voiture.class);
        when(v2.getPrix()).thenReturn(5678);
        s.ajouter(v2);

        Echantillon e = s.prixMoyen();
        assertEquals(2, e.getNombreDeVoitures());
        assertEquals(3456, e.getPrixMoyen());
    }

    @Test // Tests supplémentaires de Echantillon pour une meilleure couverture de code
    public void testModifierEchantillon()
    {
        Echantillon e = new Echantillon(3, 1234);

        e.setPrixMoyen(123);
        assertEquals(123, e.getPrixMoyen());

        e.setNombreDeVoitures(1);
        assertEquals(1, e.getNombreDeVoitures());
    }
}

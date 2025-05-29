package com.example.demo.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class VoitureTest
{
    @Test
    void creerVoiture()
    {
        Voiture v = new Voiture("Nissan", 10000);

        assertEquals("Nissan", v.getMarque());
        assertEquals(10000, v.getPrix());
    }

    @Test
    void modifierId() {
        Voiture v = new Voiture("Nissan", 11000);

        v.setId(123);

        assertEquals(123, v.getId());
    }

    @Test
    void modifierPrix()
    {
        Voiture v = new Voiture("Nissan", 10000);

        v.setPrix(11999);

        assertEquals(11999, v.getPrix());
    }

    @Test
    void modifierMarque()
    {
        Voiture v = new Voiture("Nissan", 10000);

        v.setMarque("Citroen");

        assertEquals("Citroen", v.getMarque());
    }
}

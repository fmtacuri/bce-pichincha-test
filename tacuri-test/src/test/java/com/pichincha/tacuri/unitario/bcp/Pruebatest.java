package com.pichincha.tacuri.unitario.bcp;

import com.pichincha.tacuri.ln.dao.PruebaDao;
import com.pichincha.tacuri.ln.dao.PruebaDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitPlatform.class)
public class Pruebatest {

    private static final PruebaDao prueba = new PruebaDaoImpl();

    @Test
    void retornoValor() {
        assertEquals(prueba.retornarValidacion(1), Boolean.FALSE);
        assertEquals(prueba.retornarValidacion(2), Boolean.TRUE);
    }
}

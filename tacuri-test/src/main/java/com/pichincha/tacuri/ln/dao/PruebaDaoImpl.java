package com.pichincha.tacuri.ln.dao;

import org.springframework.stereotype.Component;

@Component
public class PruebaDaoImpl implements PruebaDao {

    @Override
    public boolean retornarValidacion(Integer number) {
        return number % 2 == 0;
    }
}

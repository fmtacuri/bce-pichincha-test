package com.pichincha.tacuri.ln.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
public class BcpDetPedidoPK implements Serializable {

    private static final long serialVersionUID = 2818304419586878716L;
    private long codFactura;
    private int noFila;
}

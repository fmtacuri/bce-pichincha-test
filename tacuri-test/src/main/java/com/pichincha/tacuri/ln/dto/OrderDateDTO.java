package com.pichincha.tacuri.ln.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
@RequiredArgsConstructor
public class OrderDateDTO {
    String fechaInicio;
    String idCliente;
    String fechaFin;
}

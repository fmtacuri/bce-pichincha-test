package com.pichincha.tacuri.ln.dto;

import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
@RequiredArgsConstructor
public class ProductDTO {
    List<BcpDetPedido> listaPedidos;
    String idCliente;
    String fecha;
}

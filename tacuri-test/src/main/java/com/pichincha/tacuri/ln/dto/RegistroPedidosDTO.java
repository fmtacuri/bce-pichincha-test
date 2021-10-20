package com.pichincha.tacuri.ln.dto;

import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
@RequiredArgsConstructor
public class RegistroPedidosDTO {
    BcpHeadPedido cabecera;
    List<BcpDetPedido> listaPedidos;
    List<BcpDetPedido> listaNoGuardados;
}

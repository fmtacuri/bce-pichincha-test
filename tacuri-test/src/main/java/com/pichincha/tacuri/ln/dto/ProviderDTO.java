package com.pichincha.tacuri.ln.dto;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repository.proyectios.ProductProyection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
@RequiredArgsConstructor
public class ProviderDTO {
    BcpProveedor proveedor;
    List<ProductProyection> listaProductos;
}

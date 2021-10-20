package com.pichincha.tacuri.ln.dto;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.proyectios.ProductoProyection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
@RequiredArgsConstructor
public class ProveedorDTO {
    BcpProveedor proveedor;
    List<ProductoProyection> listaProductos;
}

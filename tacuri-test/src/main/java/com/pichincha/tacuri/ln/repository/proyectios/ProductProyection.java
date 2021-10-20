package com.pichincha.tacuri.ln.repository.proyectios;

/**
 * @author fmtacuri
 * @version 1.1
 */
public interface ProductProyection {
    String getCodProducto();
    String getDescripcion();
    int getStock();
    float getPrecio();

    void setCodProducto(String codProducto);
    void setDescripcion(String descripcion);
    void setStock(Integer stock);
    void setPrecio(Float precio);
}

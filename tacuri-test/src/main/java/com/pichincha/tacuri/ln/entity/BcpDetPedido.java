package com.pichincha.tacuri.ln.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Entity
@Table(name = "bcp_det_pedido")
@IdClass(BcpDetPedidoPK.class)
@Data
public class BcpDetPedido implements Serializable {

    private static final long serialVersionUID = -4830167376308734673L;
    @Id
    @Column(name = "cod_factura")
    private long codFactura;
    @Id
    @Column(name = "no_fila")
    private int noFila;

    private int cantidad;
    @Column(name = "precio_venta")
    private float precioVenta;
    @Column(name = "cod_inventario")
    private String codInventario;
    @JsonIgnore
    @JoinColumn(name = "cod_factura", referencedColumnName = "cod_fac", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BcpHeadPedido bcpHeadPedido;
    @JsonIgnore
    @JoinColumn(name = "cod_inventario", referencedColumnName = "id_inventario", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BcpInventario bcpInventario;
}

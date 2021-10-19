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
@Table(name = "bcp_inventario")
@Data
public class BcpInventario implements Serializable {

    private static final long serialVersionUID = -1773445774486761566L;

    @Id
    @Column(name = "id_inventario")
    private String idInventario;
    private int stock;
    private float precio;
    @Column(name = "cod_producto")
    private String codProducto;
    @Column(name = "cod_proveedor")
    private Long codProveedor;
    @JsonIgnore
    @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BcpProducto bcpProducto;
    @JsonIgnore
    @JoinColumn(name = "cod_proveedor", referencedColumnName = "cod_proveedor", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BcpProveedor bcpProveedor;
}

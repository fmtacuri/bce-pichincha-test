package com.pichincha.tacuri.ln.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Entity
@Table(name = "bcp_producto")
@Data
public class BcpProducto implements Serializable {

    private static final long serialVersionUID = -356036817903743240L;
    @Id
    @Column(name = "cod_producto")
    private String codProducto;
    private String descripcion;
}

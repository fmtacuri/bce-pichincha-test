package com.pichincha.tacuri.ln.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Freddy Tacuri
 */
@Entity
@Table(name = "bcp_proveedor")
@Data
public class BcpProveedor implements Serializable {

    private static final long serialVersionUID = 8359842415345070665L;
    @Id
    @Column(name = "cod_proveedor")
    private Long codProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;
}

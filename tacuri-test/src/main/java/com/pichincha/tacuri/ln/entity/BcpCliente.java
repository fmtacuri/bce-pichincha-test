package com.pichincha.tacuri.ln.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Freddy Tacuri
 */
@Entity
@Table(name = "bcp_cliente")
@Data
public class BcpCliente implements Serializable {

    private static final long serialVersionUID = -3717151377520610707L;
    @Id
    @Column(name = "id_cliente")
    private String idCliente;
    private String nombres;
    private long identificacion;
    private String direccion;
    private String apellidos;
}

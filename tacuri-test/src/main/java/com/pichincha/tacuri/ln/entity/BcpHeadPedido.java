package com.pichincha.tacuri.ln.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Entity
@Table(name = "bcp_head_pedido")
@Data
public class BcpHeadPedido implements Serializable {

    private static final long serialVersionUID = -4052857341104598696L;
    @Id
    @Column(name = "cod_fac")
    private Long codFac;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "id_cliente")
    private String idCliente;
    @JsonIgnore
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BcpCliente bcpCliente;
}

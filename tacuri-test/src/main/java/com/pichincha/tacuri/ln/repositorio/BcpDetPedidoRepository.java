package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpDetPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author fmtacuri
 */
@Repository
public interface BcpDetPedidoRepository extends JpaRepository<BcpDetPedido, BcpDetPedidoPK> {

    @Query(value = "select p.* from bcp_det_pedido p " +
            "where p.cod_factura = :codFactura ", nativeQuery = true)
    List<BcpDetPedido> buscarPedidoById(@Param("codFactura") Long codFactura);
}

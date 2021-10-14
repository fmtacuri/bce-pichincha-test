package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fmtacuri
 */
@Repository
public interface BcpHeadPedidoRepository extends JpaRepository<BcpHeadPedido, Long> {

    @Query(value = "select count(p.*) from bcp_head_pedido p", nativeQuery = true)
    Integer contarFacturas();
}

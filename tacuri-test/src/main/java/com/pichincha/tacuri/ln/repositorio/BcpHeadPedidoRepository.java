package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author fmtacuri
 */
@Repository
public interface BcpHeadPedidoRepository extends JpaRepository<BcpHeadPedido, Long> {

    @Query(value = "select count(p.*) from bcp_head_pedido p", nativeQuery = true)
    Integer contarFacturas();

    @Query(value = "select p.* from bcp_head_pedido p " +
            "where p.id_cliente = :cliente and fecha between :fechaInicio and :fechaFin", nativeQuery = true)
    List<BcpHeadPedido> buscarPedidosPorClienteAndFechas(@Param("cliente") String cliente,
                                                         @Param("fechaInicio") Date fechaInicio,
                                                         @Param("fechaFin") Date fechaFin);
}

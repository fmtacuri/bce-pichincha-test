package com.pichincha.tacuri.ln.repository;

import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpDetPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Repository
public interface BcpDetPedidoRepository extends JpaRepository<BcpDetPedido, BcpDetPedidoPK> {

    Optional<List<BcpDetPedido>> findBcpDetPedidoByCodFactura(Long codFactura);
}

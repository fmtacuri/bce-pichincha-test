package com.pichincha.tacuri.ln.repository;

import com.pichincha.tacuri.ln.entity.BcpProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Repository
public interface BcpProductoRepository extends JpaRepository<BcpProducto, String> {
    Optional<BcpProducto> findBcpProductoByCodProducto(String codProducto);
}

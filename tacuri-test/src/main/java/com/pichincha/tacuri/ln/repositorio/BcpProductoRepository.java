package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author fmtacuri
 */
@Repository
public interface BcpProductoRepository extends JpaRepository<BcpProducto, String> {

    @Query(value = "select p.* from bcp_producto p", nativeQuery = true)
    List<BcpProducto> buscarAllProductos();
}

package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpInventario;
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
public interface BcpInventarioRepository extends JpaRepository<BcpInventario, String> {

    @Query(value = "select p.* from bcp_inventario p", nativeQuery = true)
    List<BcpInventario> buscarAllInventario();

    @Query(value = "select p.* from bcp_inventario p " +
            "where p.cod_proveedor = :codigo", nativeQuery = true)
    List<BcpInventario> buscarProductosByProveedor(@Param("codigo") Long codigo);
}

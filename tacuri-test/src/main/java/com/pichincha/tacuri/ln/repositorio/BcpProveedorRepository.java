package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
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
public interface BcpProveedorRepository extends JpaRepository<BcpProveedor, Long> {

    @Query(value = "select p.* from bcp_proveedor p where p.cod_proveedor = :codigo", nativeQuery = true)
    BcpProveedor buscarProveedorByCodigo(@Param("codigo") Long codigo);

    @Query(value = "select p.* from bcp_proveedor p", nativeQuery = true)
    List<BcpProveedor> buscarAllProveedores();
}

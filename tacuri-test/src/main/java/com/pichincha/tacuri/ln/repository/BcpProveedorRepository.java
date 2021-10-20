package com.pichincha.tacuri.ln.repository;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Repository
public interface BcpProveedorRepository extends JpaRepository<BcpProveedor, Long> {

    Optional<BcpProveedor> findBcpProveedorByCodProveedor(Long codigo);

    @Query(value = "select p.* from bcp_proveedor p", nativeQuery = true)
    List<BcpProveedor> findBcpProveedorAll();
}

package com.pichincha.tacuri.ln.repositorio;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}

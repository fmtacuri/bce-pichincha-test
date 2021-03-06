package com.pichincha.tacuri.ln.repository;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.repository.proyectios.ProductProyection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Repository
public interface BcpInventarioRepository extends JpaRepository<BcpInventario, String> {

    @Query(value = "select bp.cod_producto as codProducto, bp.descripcion, bi.stock, bi.precio " +
            "from bcp_producto bp inner join bcp_inventario bi on bp.cod_producto = bi.cod_producto " +
            "where bi.cod_proveedor = :codigo", nativeQuery = true)
    List<ProductProyection> findProductosByProveedorProyection(@Param("codigo") Long codigo);

    Optional<BcpInventario> findBcpInventarioByIdInventario(String codigo);
}

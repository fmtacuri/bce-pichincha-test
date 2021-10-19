package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.BcpInventarioRepository;
import com.pichincha.tacuri.ln.repositorio.BcpProductoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpProveedorRepository;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class BcpProductoService {

    private final BcpProductoRepository productoRepository;

    private final BcpInventarioRepository inventarioRepository;

    private final BcpProveedorRepository proveedorRepository;

    @Transactional
    public BcpProducto saveBcpProducto(Map<String, Object> body) {
        BcpProducto bcpProducto = null;
        try {
            BcpProducto producto = JsonUtils.mapToObject(body, BcpProducto.class);
            var productoFind = productoRepository
                    .findBcpProductoByCodProducto(producto.getCodProducto()).orElse(null);
            if (Objects.isNull(productoFind)){
                bcpProducto = productoRepository.save(producto);
            }
        } catch (Exception e) {
            log.error("No se a podido guardar producto: " + body);
            throw new CustomException("Error en saveBcpProducto");
        }

        return bcpProducto;
    }

    @Transactional
    public BcpInventario saveBcpInventario(Map<String, Object> body) {
        BcpInventario bcpInventario = null;
        try {
            BcpInventario inventario = JsonUtils.mapToObject(body, BcpInventario.class);
            var inventarioFind = inventarioRepository
                    .findBcpInventarioByIdInventario(inventario.getIdInventario()).orElse(null);
            if (Objects.isNull(inventarioFind)){
                bcpInventario = inventarioRepository.save(inventario);
            }
        } catch (Exception e) {
            log.error("No se a podido guardar saveBcpInventario: " + body);
            throw new CustomException("Error en saveBcpInventario");
        }

        return bcpInventario;
    }

    public Map<String, Object> findProductosByStockAndProveedor() {
        Map<String, Object> response = new HashMap<>();
        List<BcpProveedor> listaProveedores = proveedorRepository.findBcpProveedorAll();
        listaProveedores.forEach(lp -> {
            Map<String, Object> mapProveedor = new HashMap<>();
            mapProveedor.put("proveedor", lp);
            mapProveedor.put("listaProductos", inventarioRepository.findProductosByProveedorProyection(lp.getCodProveedor()));
            response.put(lp.getCodProveedor().toString(), mapProveedor);
        });

        return response;
    }

    @Transactional
    public BcpProducto updateBcpProducto(Map<String, Object> body) {
        BcpProducto bcpProducto;
        try {
            BcpProducto producto = JsonUtils.mapToObject(body, BcpProducto.class);
            bcpProducto = productoRepository.save(producto);
        } catch (Exception e) {
            log.error("No se a podido actualizar producto: " + body);
            throw new CustomException("Error en updateBcpProducto");
        }

        return bcpProducto;
    }
}

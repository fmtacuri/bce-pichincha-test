package com.pichincha.tacuri.ln.gestor.consulta;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.BcpInventarioRepository;
import com.pichincha.tacuri.ln.repositorio.BcpProductoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fmtacuri
 */
@Component
@Log4j2
public class BcpProductoConsultaGestor {

    @Autowired
    private BcpProductoRepository productoRepository;

    @Autowired
    private BcpInventarioRepository inventarioRepository;

    @Autowired
    private BcpProveedorConsultaGestor proveedorConsultaGestor;

    public List<BcpProducto> buscarAllProductos() {
        return productoRepository.buscarAllProductos();
    }

    public List<BcpInventario> buscarAllInventario() {
        return inventarioRepository.buscarAllInventario();
    }

    public Map<String, Object> buscarProductosByProveedor() {
        Map<String, Object> response = new HashMap<>();
        List<BcpProveedor> listaProveedores = proveedorConsultaGestor.buscarAllProveedores();
        listaProveedores.forEach(lp -> {
            Map<String, Object> mapProveedor = new HashMap<>();
            mapProveedor.put("proveedor", lp);
            mapProveedor.put("listaProductos", inventarioRepository.buscarProductosByProveedor(lp.getCodProveedor()));
            response.put(lp.getCodProveedor().toString(), mapProveedor);
        });

        return response;
    }
}

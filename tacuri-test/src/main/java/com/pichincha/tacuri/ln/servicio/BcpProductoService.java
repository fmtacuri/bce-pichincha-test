package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.gestor.almacenamiento.BcpProductoAlmacenamientoGestor;
import com.pichincha.tacuri.ln.gestor.consulta.BcpProductoConsultaGestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @author fmtacuri
 */
@Service
public class BcpProductoService {

    @Autowired
    private BcpProductoAlmacenamientoGestor productoAlmacenamientoGestor;

    @Autowired
    private BcpProductoConsultaGestor productoConsultaGestor;

    @Transactional
    public List<BcpProducto> registrarProducto(Map<String, Object> body) {
        return productoAlmacenamientoGestor.registrarProducto(body);
    }

    @Transactional
    public List<BcpInventario> registrarProductoProveedor(Map<String, Object> body) {
        return productoAlmacenamientoGestor.registrarProductoProveedor(body);
    }

    public Map<String, Object> buscarProductosAndStockByProveedor() {
        return productoConsultaGestor.buscarProductosAndStockByProveedor();
    }

    @Transactional
    public List<BcpProducto> actualizarProducto(Map<String, Object> body) {
        return productoAlmacenamientoGestor.actualizarProducto(body);
    }
}

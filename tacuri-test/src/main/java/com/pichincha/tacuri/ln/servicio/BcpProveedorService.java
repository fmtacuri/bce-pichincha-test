package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.gestor.almacenamiento.BcpProveedorAlmacenamientoGestor;
import com.pichincha.tacuri.ln.gestor.consulta.BcpProveedorConsultaGestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author fmtacuri
 */
@Service
public class BcpProveedorService {

    @Autowired
    private BcpProveedorConsultaGestor proveedorConsultaGestor;

    @Autowired
    private BcpProveedorAlmacenamientoGestor proveedorAlmacenamientoGestor;

    public BcpProveedor buscarProveedorByCodigo(Long codigo) {
        return proveedorConsultaGestor.buscarProveedorByCodigo(codigo);
    }

    @Transactional
    public List<BcpProveedor> registrarProveedor(Map<String, Object> body) {
        return proveedorAlmacenamientoGestor.registrarProveedor(body);
    }

    @Transactional
    public List<BcpProveedor> actualizarProveedor(Map<String, Object> body) {
        return proveedorAlmacenamientoGestor.actualizarProveedor(body);
    }

    public List<BcpProveedor> buscarAllProveedores() {
        return proveedorConsultaGestor.buscarAllProveedores();
    }
}

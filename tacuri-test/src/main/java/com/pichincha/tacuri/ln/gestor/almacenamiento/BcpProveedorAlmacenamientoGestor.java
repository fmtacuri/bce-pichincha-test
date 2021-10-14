package com.pichincha.tacuri.ln.gestor.almacenamiento;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.gestor.consulta.BcpProveedorConsultaGestor;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @author fmtacuri
 */
@Component
@Log4j2
public class BcpProveedorAlmacenamientoGestor {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BcpProveedorConsultaGestor proveedorConsultaGestor;

    public List<BcpProveedor> registrarProveedor(Map<String, Object> body) {
        List<BcpProveedor> listaProv;
        try {
            BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);
            em.persist(proveedor);
            listaProv = proveedorConsultaGestor.buscarAllProveedores();
        } catch (Exception e) {
            log.error("No se a podido guardar proveedor: " + body);
            throw new CustomException("Error en registrarProveedor");
        }

        return listaProv;
    }

    public List<BcpProveedor> actualizarProveedor(Map<String, Object> body) {
        List<BcpProveedor> listaProv;
        try {
            BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);
            em.merge(proveedor);
            listaProv = proveedorConsultaGestor.buscarAllProveedores();
        } catch (Exception e) {
            log.error("No se a podido actualizar proveedor: " + body);
            throw new CustomException("Error en actualizarProveedor");
        }

        return listaProv;
    }
}

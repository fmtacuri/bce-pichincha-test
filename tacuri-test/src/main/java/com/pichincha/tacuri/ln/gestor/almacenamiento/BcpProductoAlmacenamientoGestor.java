package com.pichincha.tacuri.ln.gestor.almacenamiento;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.gestor.consulta.BcpProductoConsultaGestor;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fmtacuri
 */
@Component
@Log4j2
public class BcpProductoAlmacenamientoGestor {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BcpProductoConsultaGestor productoConsultaGestor;

    public List<BcpProducto> registrarProducto(Map<String, Object> body){
        List<BcpProducto> listaProv;
        try {
            BcpProducto producto = JsonUtils.mapToObject(body, BcpProducto.class);
            em.persist(producto);
            listaProv = productoConsultaGestor.buscarAllProductos();
        } catch (Exception e){
            log.error("No se a podido guardar producto: " + body);
            throw new CustomException("Error en registrarProducto");
        }

        return listaProv;
    }

    public List<BcpInventario> registrarProductoProveedor(Map<String, Object> body){
        List<BcpInventario> listaProv;
        try {
            BcpInventario producto = JsonUtils.mapToObject(body, BcpInventario.class);
            em.persist(producto);
            listaProv = productoConsultaGestor.buscarAllInventario();
        } catch (Exception e){
            log.error("No se a podido guardar registrarProductoProveedor: " + body);
            throw new CustomException("Error en registrarProductoProveedor");
        }

        return listaProv;
    }

    public List<BcpProducto> actualizarProducto(Map<String, Object> body){
        List<BcpProducto> listaProv;
        try {
            BcpProducto producto = JsonUtils.mapToObject(body, BcpProducto.class);
            em.merge(producto);
            listaProv = productoConsultaGestor.buscarAllProductos();
        } catch (Exception e){
            log.error("No se a podido actualizar producto: " + body);
            throw new CustomException("Error en actualizarProducto");
        }

        return listaProv;
    }
}

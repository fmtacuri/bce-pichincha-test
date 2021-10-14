package com.pichincha.tacuri.ln.gestor.consulta;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.repositorio.BcpInventarioRepository;
import com.pichincha.tacuri.ln.repositorio.BcpProductoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author fmtacuri
 */
@Component
@Log4j2
public class BcpProductoConsultaGestor {
    @Autowired
    private BcpProductoRepository productoRepository;

    @Autowired
    private BcpInventarioRepository inventarioRepository;

    public List<BcpProducto> buscarAllProductos() {
        return productoRepository.buscarAllProductos();
    }

    public List<BcpInventario> buscarAllInventario() {
        return inventarioRepository.buscarAllInventario();
    }
}

package com.pichincha.tacuri.ln.gestor.consulta;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.BcpProveedorRepository;
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
public class BcpProveedorConsultaGestor {
    @Autowired
    private BcpProveedorRepository proveedorRepository;

    public BcpProveedor buscarProveedorByCodigo(Long codigo) {
        return proveedorRepository.buscarProveedorByCodigo(codigo);
    }

    public List<BcpProveedor> buscarAllProveedores() {
        return proveedorRepository.buscarAllProveedores();
    }
}

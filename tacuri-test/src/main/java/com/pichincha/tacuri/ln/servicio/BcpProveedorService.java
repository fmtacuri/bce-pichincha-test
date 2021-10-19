package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.BcpProveedorRepository;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class BcpProveedorService {

    private final BcpProveedorRepository proveedorRepository;

    public BcpProveedor findBcpProveedorByCodProveedor(Long codigo) {
        return proveedorRepository.findBcpProveedorByCodProveedor(codigo);
    }

    @Transactional
    public BcpProveedor saveBcpProveedor(Map<String, Object> body) {
        BcpProveedor bcpProveedor;
        try {
            BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);
            bcpProveedor = proveedorRepository.save(proveedor);
        } catch (Exception e) {
            log.error("No se a podido guardar proveedor: " + body);
            throw new CustomException("Error en registrarProveedor");
        }

        return bcpProveedor;
    }

    @Transactional
    public BcpProveedor updateBcpProveedor(Map<String, Object> body) {
        BcpProveedor bcpProveedor;
        try {
            BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);
            bcpProveedor = proveedorRepository.save(proveedor);
        } catch (Exception e) {
            log.error("No se a podido actualizar proveedor: " + body);
            throw new CustomException("Error en actualizarProveedor");
        }

        return bcpProveedor;
    }
}

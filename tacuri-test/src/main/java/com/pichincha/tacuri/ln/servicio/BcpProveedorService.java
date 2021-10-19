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
import java.util.Objects;

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
        return proveedorRepository.findBcpProveedorByCodProveedor(codigo).orElse(null);
    }

    @Transactional
    public BcpProveedor saveBcpProveedor(Map<String, Object> body) {
        BcpProveedor bcpProveedor = null;
        try {
            BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);
            var proveedorFind = proveedorRepository
                    .findBcpProveedorByCodProveedor(proveedor.getCodProveedor()).orElse(null);
            if (Objects.isNull(proveedorFind)){
                bcpProveedor = proveedorRepository.save(proveedor);
            }
        } catch (Exception e) {
            log.error("No se a podido guardar proveedor: " + body);
            throw new CustomException("Error en saveBcpProveedor");
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
            throw new CustomException("Error en updateBcpProveedor");
        }

        return bcpProveedor;
    }
}

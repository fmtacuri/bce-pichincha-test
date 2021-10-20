package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.BcpProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BcpProveedor saveBcpProveedor(BcpProveedor proveedor) {
        BcpProveedor bcpProveedor = null;
        try {
            var proveedorFind = proveedorRepository
                    .findBcpProveedorByCodProveedor(proveedor.getCodProveedor()).orElse(null);
            if (Objects.isNull(proveedorFind)){
                bcpProveedor = proveedorRepository.save(proveedor);
            }
        } catch (Exception e) {
            log.error("No se a podido guardar proveedor: " + proveedor.getCodProveedor());
            throw new CustomException("Error en saveBcpProveedor");
        }

        return bcpProveedor;
    }

    @Transactional
    public BcpProveedor updateBcpProveedor(BcpProveedor proveedor) {
        BcpProveedor bcpProveedor;
        try {
            bcpProveedor = proveedorRepository.save(proveedor);
        } catch (Exception e) {
            log.error("No se a podido actualizar proveedor: " + proveedor.getCodProveedor());
            throw new CustomException("Error en updateBcpProveedor");
        }

        return bcpProveedor;
    }
}

package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repository.BcpProveedorRepository;
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
public class ProviderService {

    private final BcpProveedorRepository providerRepository;

    public BcpProveedor findBcpProveedorByCodProveedor(Long codigo) {
        return providerRepository.findBcpProveedorByCodProveedor(codigo).orElse(null);
    }

    @Transactional
    public BcpProveedor saveBcpProveedor(BcpProveedor proveedor) {
        BcpProveedor bcpProveedor = null;
        try {
            var proveedorFind = providerRepository
                    .findBcpProveedorByCodProveedor(proveedor.getCodProveedor()).orElse(null);
            if (Objects.isNull(proveedorFind)){
                bcpProveedor = providerRepository.save(proveedor);
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
            bcpProveedor = providerRepository.save(proveedor);
        } catch (Exception e) {
            log.error("No se a podido actualizar proveedor: " + proveedor.getCodProveedor());
            throw new CustomException("Error en updateBcpProveedor");
        }

        return bcpProveedor;
    }
}

package com.pichincha.tacuri.ln.service;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repository.BcpProveedorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Component
@Log4j2
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private BcpProveedorRepository providerRepository;

    public BcpProveedor findBcpProveedorByCodProveedor(Long codigo) {
        return providerRepository.findBcpProveedorByCodProveedor(codigo).orElse(null);
    }

    @Transactional
    @Override
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
    @Override
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

package com.pichincha.tacuri.ln.service;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import org.springframework.stereotype.Service;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
public interface ProviderService {

    BcpProveedor findBcpProveedorByCodProveedor(Long codigo);

    BcpProveedor saveBcpProveedor(BcpProveedor proveedor);

    BcpProveedor updateBcpProveedor(BcpProveedor proveedor);
}

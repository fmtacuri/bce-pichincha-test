package com.pichincha.tacuri.ln.service;

import com.pichincha.tacuri.ln.dto.ProviderDTO;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
public interface ProductService {

    BcpProducto saveBcpProduct(BcpProducto producto);

    BcpInventario saveBcpInventary(BcpInventario inventario);

    List<ProviderDTO> findProductosByStockAndProveedor();

    BcpProducto updateProduct(BcpProducto producto);
}

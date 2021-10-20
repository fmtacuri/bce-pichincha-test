package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.dto.ProviderDTO;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repository.BcpInventarioRepository;
import com.pichincha.tacuri.ln.repository.BcpProductoRepository;
import com.pichincha.tacuri.ln.repository.BcpProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final BcpProductoRepository productRepository;

    private final BcpInventarioRepository inventaryRepository;

    private final BcpProveedorRepository providerRepository;

    @Transactional
    public BcpProducto saveBcpProduct(BcpProducto producto) {
        BcpProducto bcpProducto = null;
        var productoFind = productRepository
                .findBcpProductoByCodProducto(producto.getCodProducto()).orElse(null);
        if (Objects.isNull(productoFind)) {
            bcpProducto = productRepository.save(producto);
        } else {
            log.error("No se a podido guardar producto: " + producto.getCodProducto());
            throw new CustomException("Error en saveBcpProducto");
        }

        return bcpProducto;
    }

    @Transactional
    public BcpInventario saveBcpInventary(BcpInventario inventario) {
        BcpInventario bcpInventario = null;
        var inventarioFind = inventaryRepository
                .findBcpInventarioByIdInventario(inventario.getIdInventario()).orElse(null);
        if (Objects.isNull(inventarioFind)) {
            bcpInventario = inventaryRepository.save(inventario);
        } else {
            log.error("No se a podido guardar saveBcpInventario: " + inventario.getIdInventario());
            throw new CustomException("Error en saveBcpInventario");
        }

        return bcpInventario;
    }

    public List<ProviderDTO> findProductosByStockAndProveedor() {
        List<ProviderDTO> listaDto = new ArrayList<>();
        List<BcpProveedor> listaProveedores = providerRepository.findBcpProveedorAll();
        listaProveedores.forEach(lp -> {
            ProviderDTO providerDTO = new ProviderDTO();
            providerDTO.setProveedor(lp);
            providerDTO.setListaProductos(inventaryRepository.findProductosByProveedorProyection(lp.getCodProveedor()));
            listaDto.add(providerDTO);
        });

        return listaDto;
    }

    @Transactional
    public BcpProducto updateProduct(BcpProducto producto) {
        BcpProducto bcpProducto;
        try {
            bcpProducto = productRepository.save(producto);
        } catch (Exception e) {
            log.error("No se a podido actualizar producto: " + producto.getCodProducto());
            throw new CustomException("Error en updateBcpProducto");
        }

        return bcpProducto;
    }
}

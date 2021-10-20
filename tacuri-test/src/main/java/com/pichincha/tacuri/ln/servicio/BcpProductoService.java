package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.dto.ProveedorDTO;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repositorio.BcpInventarioRepository;
import com.pichincha.tacuri.ln.repositorio.BcpProductoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpProveedorRepository;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class BcpProductoService {

    private final BcpProductoRepository productoRepository;

    private final BcpInventarioRepository inventarioRepository;

    private final BcpProveedorRepository proveedorRepository;

    @Transactional
    public BcpProducto saveBcpProducto(BcpProducto producto) {
        BcpProducto bcpProducto = null;
        try {
            var productoFind = productoRepository
                    .findBcpProductoByCodProducto(producto.getCodProducto()).orElse(null);
            if (Objects.isNull(productoFind)){
                bcpProducto = productoRepository.save(producto);
            }
        } catch (Exception e) {
            log.error("No se a podido guardar producto: " + producto.getCodProducto());
            throw new CustomException("Error en saveBcpProducto");
        }

        return bcpProducto;
    }

    @Transactional
    public BcpInventario saveBcpInventario(BcpInventario inventario) {
        BcpInventario bcpInventario = null;
        try {
            var inventarioFind = inventarioRepository
                    .findBcpInventarioByIdInventario(inventario.getIdInventario()).orElse(null);
            if (Objects.isNull(inventarioFind)){
                bcpInventario = inventarioRepository.save(inventario);
            }
        } catch (Exception e) {
            log.error("No se a podido guardar saveBcpInventario: " + inventario.getIdInventario());
            throw new CustomException("Error en saveBcpInventario");
        }

        return bcpInventario;
    }

    public List<ProveedorDTO> findProductosByStockAndProveedor() {
        List<ProveedorDTO> listaDto = new ArrayList<>();
        List<BcpProveedor> listaProveedores = proveedorRepository.findBcpProveedorAll();
        listaProveedores.forEach(lp -> {
            ProveedorDTO proveedorDTO = new ProveedorDTO();
            proveedorDTO.setProveedor(lp);
            proveedorDTO.setListaProductos(inventarioRepository.findProductosByProveedorProyection(lp.getCodProveedor()));
            listaDto.add(proveedorDTO);
        });

        return listaDto;
    }

    @Transactional
    public BcpProducto updateBcpProducto(BcpProducto producto) {
        BcpProducto bcpProducto;
        try {
            bcpProducto = productoRepository.save(producto);
        } catch (Exception e) {
            log.error("No se a podido actualizar producto: " + producto.getCodProducto());
            throw new CustomException("Error en updateBcpProducto");
        }

        return bcpProducto;
    }
}

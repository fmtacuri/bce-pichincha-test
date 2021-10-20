package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.dto.ProveedorDTO;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.servicio.BcpProductoService;
import com.pichincha.tacuri.util.BceConstant;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/producto")
@RequiredArgsConstructor
public class BcpProductoController {

    private final BcpProductoService productoService;

    @PostMapping("/registrar-producto")
    public ResponseEntity<BcpProducto> saveBcpProducto(@NotNull @RequestBody BcpProducto producto) {
        ResponseEntity response;
        var requestValue = productoService.saveBcpProducto(producto);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @PostMapping("/registrar-producto-proveedor")
    public ResponseEntity<BcpInventario> saveBcpInventario(@NotNull @RequestBody BcpInventario inventario) {
        ResponseEntity response;
        var requestValue = productoService.saveBcpInventario(inventario);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping("/buscar-proveedor-producto")
    public ResponseEntity<List<ProveedorDTO>> findProductosByStockAndProveedor() {
        return new ResponseEntity<>(productoService.findProductosByStockAndProveedor(), HttpStatus.OK);
    }

    @PutMapping("/actualizar-producto")
    public ResponseEntity<BcpProducto> updateBcpProducto(@NotNull @RequestBody BcpProducto producto) {
        return new ResponseEntity<>(productoService.updateBcpProducto(producto), HttpStatus.OK);
    }
}
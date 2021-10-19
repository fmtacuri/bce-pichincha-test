package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.servicio.BcpProductoService;
import com.pichincha.tacuri.util.BceConstant;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ResponseEntity<BcpProducto> saveBcpProducto(@NotNull @RequestBody Map<String, Object> body) {
        ResponseEntity response;
        var requestValue = productoService.saveBcpProducto(body);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @PostMapping("/registrar-producto-proveedor")
    public ResponseEntity<BcpInventario> saveBcpInventario(@NotNull @RequestBody Map<String, Object> body) {
        ResponseEntity response;
        var requestValue = productoService.saveBcpInventario(body);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping("/buscar-proveedor-producto")
    public ResponseEntity<Map<String, Object>> findProductosByStockAndProveedor() {
        return new ResponseEntity<>(productoService.findProductosByStockAndProveedor(), HttpStatus.OK);
    }

    @PutMapping("/actualizar-producto")
    public ResponseEntity<BcpProducto> updateBcpProducto(@NotNull @RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(productoService.updateBcpProducto(body), HttpStatus.OK);
    }
}
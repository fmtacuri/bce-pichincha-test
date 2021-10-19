package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.servicio.BcpProductoService;
import com.pichincha.tacuri.util.BceConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<BcpProducto> saveBcpProducto(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(productoService.saveBcpProducto(body), HttpStatus.OK);
    }

    @PostMapping("/registrar-producto-proveedor")
    public ResponseEntity<BcpInventario> saveBcpInventario(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(productoService.saveBcpInventario(body), HttpStatus.OK);
    }

    @GetMapping("/buscar-proveedor-producto")
    public ResponseEntity<Map<String, Object>> findProductosByStockAndProveedor() {
        return new ResponseEntity<>(productoService.findProductosByStockAndProveedor(), HttpStatus.OK);
    }

    @PutMapping("/actualizar-producto")
    public ResponseEntity<BcpProducto> updateBcpProducto(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(productoService.updateBcpProducto(body), HttpStatus.OK);
    }
}
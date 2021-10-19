package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.servicio.BcpProveedorService;
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
@RequestMapping(BceConstant.URL_PATH + "/proveedor")
@RequiredArgsConstructor
public class BcpProveedorController {

    private final BcpProveedorService proveedorService;

    @GetMapping("/{codigo}")
    public ResponseEntity<BcpProveedor> findBcpProveedorByCodProveedor(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(proveedorService.findBcpProveedorByCodProveedor(codigo), HttpStatus.OK);
    }

    @PostMapping("/guardar-proveedor")
    public ResponseEntity<BcpProveedor> saveBcpProveedor(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(proveedorService.saveBcpProveedor(body), HttpStatus.OK);
    }

    @PutMapping("/actualizar-proveedor")
    public ResponseEntity<BcpProveedor> updateBcpProveedor(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(proveedorService.updateBcpProveedor(body), HttpStatus.OK);
    }
}
package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.servicio.BcpProveedorService;
import com.pichincha.tacuri.util.BceConstant;
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
@RequestMapping(BceConstant.URL_PATH + "/proveedor")
@RequiredArgsConstructor
public class BcpProveedorController {

    private final BcpProveedorService proveedorService;

    @GetMapping("/{codigo}")
    public ResponseEntity<BcpProveedor> findBcpProveedorByCodProveedor(@PathVariable("codigo") Long codigo) {
        ResponseEntity response;
        var requestValue = proveedorService.findBcpProveedorByCodProveedor(codigo);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
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
package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.servicio.BcpProveedorService;
import com.pichincha.tacuri.util.BceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 * @author fmtacuri
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/proveedor")
public class BcpProveedorController {
    @Autowired
    private BcpProveedorService proveedorService;

    @GetMapping("/{codigo}")
    public ResponseEntity<BcpProveedor> buscarProveedorByCodigo(@PathVariable("codigo") Long codigo) {
        return new ResponseEntity<>(proveedorService.buscarProveedorByCodigo(codigo), HttpStatus.OK);
    }

    @PostMapping("/guardar-proveedor")
    public ResponseEntity<List<BcpProveedor>> registrarProveedor(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(proveedorService.registrarProveedor(body), HttpStatus.OK);
    }

    @PutMapping("/actualizar-proveedor")
    public ResponseEntity<List<BcpProveedor> > actualizarProveedor(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(proveedorService.actualizarProveedor(body), HttpStatus.OK);
    }
}
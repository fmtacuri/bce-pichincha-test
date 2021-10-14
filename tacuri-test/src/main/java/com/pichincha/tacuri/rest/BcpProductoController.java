package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.servicio.BcpProductoService;
import com.pichincha.tacuri.util.BceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * @author fmtacuri
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/producto")
public class BcpProductoController {
    @Autowired
    private BcpProductoService productoService;

    @PostMapping("/registrar-producto")
    public ResponseEntity<List<BcpProducto>> registrarProducto(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(productoService.registrarProducto(body), HttpStatus.OK);
    }

    @PostMapping("/registrar-producto-proveedor")
    public ResponseEntity<List<BcpInventario>> registrarProductoProveedor(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(productoService.registrarProductoProveedor(body), HttpStatus.OK);
    }

}
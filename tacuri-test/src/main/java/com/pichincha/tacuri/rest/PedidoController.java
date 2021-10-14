package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.servicio.PedidosService;
import com.pichincha.tacuri.util.BceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @author fmtacuri
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/pedido")
public class PedidoController {
    @Autowired
    private PedidosService pedidosService;

    @PostMapping("/guardar-pedido")
    public ResponseEntity<Map<String, Object>> registrarPedido(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(pedidosService.registrarPedido(body), HttpStatus.OK);
    }

    @PostMapping("/buscar-pedidos-fecha")
    public ResponseEntity<Map<String, Object>> recuperarPedidosPorClienteAndFecha(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(pedidosService.recuperarPedidosPorClienteAndFecha(body), HttpStatus.OK);
    }
}
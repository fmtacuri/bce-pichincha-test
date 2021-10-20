package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.dto.RegistroPedidosDTO;
import com.pichincha.tacuri.ln.servicio.PedidosService;
import com.pichincha.tacuri.util.BceConstant;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author fmtacuri
 * @version 1.1
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidosService pedidosService;

    @PostMapping("/guardar-pedido")
    public ResponseEntity<RegistroPedidosDTO> save(@NotNull @RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(pedidosService.save(body), HttpStatus.OK);
    }

    @PostMapping("/buscar-pedidos-fecha")
    public ResponseEntity<List<RegistroPedidosDTO>> findPedidosByIdClienteAndFecha(@NotNull @RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(pedidosService.findPedidosByIdClienteAndFecha(body), HttpStatus.OK);
    }

    @DeleteMapping("eliminar-pedido/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") Long id) {
        pedidosService.deletePedido(id);
        return new ResponseEntity<>("OK: Registro eliminado correctamente.", HttpStatus.OK);
    }
}
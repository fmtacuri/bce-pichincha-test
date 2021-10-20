package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.dto.PedidoDTO;
import com.pichincha.tacuri.ln.dto.PedidoFechaDTO;
import com.pichincha.tacuri.ln.dto.ProductoDTO;
import com.pichincha.tacuri.ln.servicio.PedidosService;
import com.pichincha.tacuri.util.BceConstant;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PedidoDTO> save(@NotNull @RequestBody ProductoDTO productoDTO) {
        return new ResponseEntity<>(pedidosService.save(productoDTO), HttpStatus.OK);
    }

    @PostMapping("/buscar-pedidos-fecha")
    public ResponseEntity<List<PedidoDTO>> findPedidosByIdClienteAndFecha(@NotNull @RequestBody PedidoFechaDTO pedidoFechaDTO) {
        return new ResponseEntity<>(pedidosService.findPedidosByIdClienteAndFecha(pedidoFechaDTO), HttpStatus.OK);
    }

    @DeleteMapping("eliminar-pedido/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") Long id) {
        pedidosService.deletePedido(id);
        return new ResponseEntity<>("OK: Registro eliminado correctamente.", HttpStatus.OK);
    }
}
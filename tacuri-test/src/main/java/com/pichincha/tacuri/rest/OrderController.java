package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.dto.OrderDTO;
import com.pichincha.tacuri.ln.dto.OrderDateDTO;
import com.pichincha.tacuri.ln.dto.ProductDTO;
import com.pichincha.tacuri.ln.servicio.OrderService;
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
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save-order")
    public ResponseEntity<OrderDTO> saveOrder(@NotNull @RequestBody ProductDTO productoDTO) {
        return new ResponseEntity<>(orderService.saveOrder(productoDTO), HttpStatus.OK);
    }

    @PostMapping("/find-order-date")
    public ResponseEntity<List<OrderDTO>> findPedidosByIdClienteAndFecha(@NotNull @RequestBody OrderDateDTO orderDateDTO) {
        return new ResponseEntity<>(orderService.findPedidosByIdClienteAndFecha(orderDateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>("OK: Registro eliminado correctamente.", HttpStatus.OK);
    }
}
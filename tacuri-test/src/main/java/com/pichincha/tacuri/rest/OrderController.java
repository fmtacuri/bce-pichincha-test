package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.dto.OrderDTO;
import com.pichincha.tacuri.ln.dto.OrderDateDTO;
import com.pichincha.tacuri.ln.dto.ProductDTO;
import com.pichincha.tacuri.ln.service.OrderService;
import com.pichincha.tacuri.ln.service.OrderServiceImpl;
import com.pichincha.tacuri.util.BceConstant;
import com.pichincha.tacuri.util.factory.BeanFactory;
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

    private OrderService service = BeanFactory.getBean(OrderServiceImpl.class);

    @PostMapping("/save-order")
    public ResponseEntity<OrderDTO> saveOrder(@NotNull @RequestBody ProductDTO productoDTO) {
        return new ResponseEntity<>(service.saveOrder(productoDTO), HttpStatus.OK);
    }

    @PostMapping("/find-order-date")
    public ResponseEntity<List<OrderDTO>> findPedidosByIdClienteAndFecha(@NotNull @RequestBody OrderDateDTO orderDateDTO) {
        return new ResponseEntity<>(service.findPedidosByIdClienteAndFecha(orderDateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        service.deleteOrder(id);
        return new ResponseEntity<>("OK: Registro eliminado correctamente.", HttpStatus.OK);
    }
}
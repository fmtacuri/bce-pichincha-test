package com.pichincha.tacuri.ln.service;

import com.pichincha.tacuri.ln.dto.OrderDTO;
import com.pichincha.tacuri.ln.dto.OrderDateDTO;
import com.pichincha.tacuri.ln.dto.ProductDTO;
import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
public interface OrderService {

    OrderDTO saveOrder(ProductDTO productoDTO);

    List<OrderDTO> findPedidosByIdClienteAndFecha(OrderDateDTO orderDateDTO);

    void deleteOrder(Long id);

    OrderDTO registerPedido(BcpHeadPedido cabecera, List<BcpDetPedido> listaPedidos, Long contador);
}

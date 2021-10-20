package com.pichincha.tacuri.ln.service;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.dto.OrderDTO;
import com.pichincha.tacuri.ln.dto.OrderDateDTO;
import com.pichincha.tacuri.ln.dto.ProductDTO;
import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpDetPedidoPK;
import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import com.pichincha.tacuri.ln.repository.BcpDetPedidoRepository;
import com.pichincha.tacuri.ln.repository.BcpHeadPedidoRepository;
import com.pichincha.tacuri.ln.repository.BcpInventarioRepository;
import com.pichincha.tacuri.util.BceConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Component
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BcpHeadPedidoRepository bcpHeadPedidoRepository;

    @Autowired
    private BcpDetPedidoRepository bcpDetPedidoRepository;

    @Autowired
    private BcpInventarioRepository inventaryRepository;

    @Transactional
    public OrderDTO saveOrder(ProductDTO productoDTO) {
        OrderDTO response;
        try {
            if (!productoDTO.getListaPedidos().isEmpty()) {
                Date fecha = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD)
                        .parse(productoDTO.getFecha());
                Integer contadorFact = bcpHeadPedidoRepository.countAllPedidos() + 1;

                BcpHeadPedido cabecera = new BcpHeadPedido();
                cabecera.setCodFac(contadorFact.longValue());
                cabecera.setFecha(fecha);
                cabecera.setIdCliente(productoDTO.getIdCliente());

                response = registerPedido(cabecera, productoDTO.getListaPedidos(), contadorFact.longValue());

            } else {
                log.warn("No se puede Guardar un pedido sin detalles");
                throw new CustomException("No se puede Guardar un pedido sin detalles");
            }
        } catch (Exception e) {
            log.error("No se a podido guardar el pedido: " + productoDTO.getIdCliente());
            throw new CustomException("Error en registrarPedido");
        }

        return response;
    }

    public List<OrderDTO> findPedidosByIdClienteAndFecha(OrderDateDTO orderDateDTO) {
        List<OrderDTO> listaPedidosRecuperados = new ArrayList<>();
        try {
            Date fechaInicio = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(orderDateDTO.getFechaInicio());
            Date fechaFin = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(orderDateDTO.getFechaFin());

            List<BcpHeadPedido> listaCabeceraPedidos = bcpHeadPedidoRepository
                    .findBcpHeadPedidoByIdClienteAndFecha(orderDateDTO.getIdCliente(), fechaInicio, fechaFin);

            listaCabeceraPedidos.forEach(lp -> {
                OrderDTO pedidosDTO = new OrderDTO();
                pedidosDTO.setCabecera(lp);
                pedidosDTO.setListaPedidos(bcpDetPedidoRepository.findBcpDetPedidoByCodFactura(lp.getCodFac())
                        .orElse(Collections.emptyList()));
                pedidosDTO.setListaNoGuardados(Collections.emptyList());
                listaPedidosRecuperados.add(pedidosDTO);
            });
        } catch (Exception e) {
            log.error("Error en al recuperarPedidosPorClienteAndFecha");
        }

        return listaPedidosRecuperados;
    }

    @Transactional
    public void deleteOrder(Long id) {
        try {
            var listaDetalles = bcpDetPedidoRepository.findBcpDetPedidoByCodFactura(id)
                    .orElse(Collections.emptyList());
            listaDetalles.forEach(ld -> {
                BcpDetPedidoPK pedidoPK = new BcpDetPedidoPK();
                pedidoPK.setCodFactura(ld.getCodFactura());
                pedidoPK.setNoFila(ld.getNoFila());
                bcpDetPedidoRepository.deleteById(pedidoPK);
            });

            bcpHeadPedidoRepository.deleteById(id);

        } catch (Exception e) {
            throw new CustomException("No puede eliminar el registro indicado!");
        }
    }

    public OrderDTO registerPedido(BcpHeadPedido cabecera, List<BcpDetPedido> listaPedidos, Long contador) {
        List<BcpDetPedido> listaGuardados = new ArrayList<>();
        List<BcpDetPedido> listaNoGuardados = new ArrayList<>();
        OrderDTO registroPedidosDTO = new OrderDTO();

        registroPedidosDTO.setCabecera(bcpHeadPedidoRepository.save(cabecera));
        AtomicInteger index = new AtomicInteger();

        listaPedidos.forEach(lp -> {
            var productoInventario = inventaryRepository
                    .findBcpInventarioByIdInventario(lp.getCodInventario()).orElse(null);
            if (!Objects.isNull(productoInventario) && productoInventario.getStock() > lp.getCantidad()) {
                index.getAndIncrement();
                lp.setCodFactura(contador);
                lp.setNoFila(index.get());
                BcpDetPedido bcpDetPedido = bcpDetPedidoRepository.save(lp);
                listaGuardados.add(bcpDetPedido);
                productoInventario.setStock(productoInventario.getStock() - lp.getCantidad());
                inventaryRepository.save(productoInventario);
            } else {
                log.warn("No se puede Guardar el pedido: " + lp.getCodInventario() + ", por falta de stock");
                listaNoGuardados.add(lp);
            }
        });
        registroPedidosDTO.setListaPedidos(listaGuardados);
        registroPedidosDTO.setListaNoGuardados(listaNoGuardados);

        return registroPedidosDTO;
    }
}

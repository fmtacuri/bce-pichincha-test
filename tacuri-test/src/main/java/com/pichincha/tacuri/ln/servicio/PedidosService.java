package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpDetPedidoPK;
import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import com.pichincha.tacuri.ln.repositorio.BcpDetPedidoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpHeadPedidoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpInventarioRepository;
import com.pichincha.tacuri.util.BceConstant;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class PedidosService {

    private final BcpHeadPedidoRepository cabeceraPedido;

    private final BcpDetPedidoRepository detallePedido;

    private final BcpInventarioRepository inventarioRepository;

    @Transactional
    public Map<String, Object> save(Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<BcpDetPedido> listaPedidos = JsonUtils.jsonToList(body.get("listaPedidos"), BcpDetPedido.class);
            List<BcpDetPedido> listaGuardados = new ArrayList<>();
            List<BcpDetPedido> listaNoGuardados = new ArrayList<>();
            if (!listaPedidos.isEmpty()) {
                AtomicInteger index = new AtomicInteger();
                String idCliente = body.get("idCliente").toString();
                Date fecha = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD)
                        .parse(body.get("fecha").toString());
                Integer contadorFact = cabeceraPedido.countAllBy() + 1;

                BcpHeadPedido cabecera = new BcpHeadPedido();
                cabecera.setCodFac(contadorFact.longValue());
                cabecera.setFecha(fecha);
                cabecera.setIdCliente(idCliente);
                var cabeceraResponse = cabeceraPedido.save(cabecera);

                listaPedidos.forEach(lp -> {
                    var productoInventario = inventarioRepository.findBcpInventarioByIdInventario(lp.getCodInventario());
                    if (productoInventario.getStock() > lp.getCantidad()) {
                        index.getAndIncrement();
                        lp.setCodFactura(contadorFact.longValue());
                        lp.setNoFila(index.get());
                        BcpDetPedido bcpDetPedido = detallePedido.save(lp);
                        listaGuardados.add(bcpDetPedido);
                        productoInventario.setStock(productoInventario.getStock() - lp.getCantidad());
                        inventarioRepository.save(productoInventario);
                    } else {
                        log.warn("No se puede Guardar el pedido: " + lp.getCodInventario() + ", por falta de stock");
                        listaNoGuardados.add(lp);
                    }
                });

                response.put("Cabecera_Pedido", cabeceraResponse);
                response.put("Pedidos_Guardados", listaGuardados);
                response.put("Pedidos_No_Guardados", listaNoGuardados);
            } else {
                log.warn("No se puede Guardar un pedido sin detalles");
                throw new CustomException("No se puede Guardar un pedido sin detalles");
            }
        } catch (Exception e) {
            log.error("No se a podido guardar el pedido: " + body);
            throw new CustomException("Error en registrarPedido");
        }

        return response;
    }

    public Map<String, Object> findPedidosByIdClienteAndFecha(Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            Date fechaInicio = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(body.get("fechaInicio").toString());
            Date fechaFin = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(body.get("fechaFin").toString());
            String idCliente = body.get("idCliente").toString();

            List<BcpHeadPedido> listaCabeceraPedidos = cabeceraPedido
                    .findBcpHeadPedidoByIdClienteAndFecha(idCliente, fechaInicio, fechaFin);

            listaCabeceraPedidos.forEach(lp -> {
                Map<String, Object> mapProveedor = new HashMap<>();
                mapProveedor.put("Pedido", lp);
                mapProveedor.put("DetallePedido", detallePedido.findBcpDetPedidoByCodFactura(lp.getCodFac()));
                response.put(lp.getCodFac().toString(), mapProveedor);
            });
        } catch (Exception e) {
            log.error("Error en al recuperarPedidosPorClienteAndFecha");
        }

        return response;
    }

    @Transactional
    public void deletePedido(Long id) {
        try {
            var listaDetalles = detallePedido.findBcpDetPedidoByCodFactura(id);
            listaDetalles.forEach(ld -> {
                BcpDetPedidoPK pedidoPK = new BcpDetPedidoPK();
                pedidoPK.setCodFactura(ld.getCodFactura());
                pedidoPK.setNoFila(ld.getNoFila());
                detallePedido.deleteById(pedidoPK);
            });

            cabeceraPedido.deleteById(id);

        } catch (Exception e) {
            throw new CustomException("No puede eliminar el registro indicado!");
        }
    }
}

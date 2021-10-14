package com.pichincha.tacuri.ln.gestor.almacenamiento;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpDetPedidoPK;
import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import com.pichincha.tacuri.ln.gestor.consulta.BcpProductoConsultaGestor;
import com.pichincha.tacuri.ln.gestor.consulta.PedidosConsultaGestor;
import com.pichincha.tacuri.ln.repositorio.BcpDetPedidoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpHeadPedidoRepository;
import com.pichincha.tacuri.util.BceConstant;
import com.pichincha.tacuri.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fmtacuri
 */
@Component
@Log4j2
public class PedidoAlmacenamientoGestor {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PedidosConsultaGestor pedidosConsultaGestor;

    @Autowired
    private BcpProductoConsultaGestor productoConsultaGestor;

    @Autowired
    private BcpHeadPedidoRepository cabeceraPedido;

    @Autowired
    private BcpDetPedidoRepository detallePedido;

    public Map<String, Object> registrarPedido(Map<String, Object> body) {
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
                Integer contadorFact = pedidosConsultaGestor.contarFacturas() + 1;

                BcpHeadPedido cabecera = new BcpHeadPedido();
                cabecera.setCodFac(contadorFact.longValue());
                cabecera.setFecha(fecha);
                cabecera.setIdCliente(idCliente);
                em.persist(cabecera);

                listaPedidos.forEach(lp -> {
                    var productoInventario = productoConsultaGestor.buscarProductosByCodigo(lp.getCodInventario());
                    if (productoInventario.getStock() > lp.getCantidad()) {
                        index.getAndIncrement();
                        lp.setCodFactura(contadorFact.longValue());
                        lp.setNoFila(index.get());
                        listaGuardados.add(lp);
                        em.persist(lp);
                        productoInventario.setStock(productoInventario.getStock() - lp.getCantidad());
                        em.merge(productoInventario);
                    } else {
                        log.warn("No se puede Guardar el pedido: " + lp.getCodInventario() + ", por falta de stock");
                        listaNoGuardados.add(lp);
                    }
                });

                response.put("Cabecera_Pedido", cabecera);
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

    public void eliminarPedido(Long id) {
        try {
            var listaDetalles = detallePedido.buscarPedidoById(id);
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

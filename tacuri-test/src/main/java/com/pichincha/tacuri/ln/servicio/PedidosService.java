package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.dto.RegistroPedidosDTO;
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
    public RegistroPedidosDTO save(Map<String, Object> body) {
        RegistroPedidosDTO response;
        try {
            List<BcpDetPedido> listaPedidos = JsonUtils.jsonToList(body.get("listaPedidos"), BcpDetPedido.class);

            if (!listaPedidos.isEmpty()) {
                String idCliente = body.get("idCliente").toString();
                Date fecha = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD)
                        .parse(body.get("fecha").toString());
                Integer contadorFact = cabeceraPedido.countAllPedidos() + 1;

                BcpHeadPedido cabecera = new BcpHeadPedido();
                cabecera.setCodFac(contadorFact.longValue());
                cabecera.setFecha(fecha);
                cabecera.setIdCliente(idCliente);

                response = registerPedido(cabecera, listaPedidos, contadorFact.longValue());

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

    public List<RegistroPedidosDTO> findPedidosByIdClienteAndFecha(Map<String, Object> body) {
        List<RegistroPedidosDTO> listaPedidosRecuperados = new ArrayList<>();
        try {
            Date fechaInicio = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(body.get("fechaInicio").toString());
            Date fechaFin = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(body.get("fechaFin").toString());
            String idCliente = body.get("idCliente").toString();

            List<BcpHeadPedido> listaCabeceraPedidos = cabeceraPedido
                    .findBcpHeadPedidoByIdClienteAndFecha(idCliente, fechaInicio, fechaFin);

            listaCabeceraPedidos.forEach(lp -> {
                RegistroPedidosDTO pedidosDTO = new RegistroPedidosDTO();
                pedidosDTO.setCabecera(lp);
                pedidosDTO.setListaPedidos(detallePedido.findBcpDetPedidoByCodFactura(lp.getCodFac())
                        .orElse(Collections.emptyList()));
                listaPedidosRecuperados.add(pedidosDTO);
            });
        } catch (Exception e) {
            log.error("Error en al recuperarPedidosPorClienteAndFecha");
        }

        return listaPedidosRecuperados;
    }

    @Transactional
    public void deletePedido(Long id) {
        try {
            var listaDetalles = detallePedido.findBcpDetPedidoByCodFactura(id)
                    .orElse(Collections.emptyList());
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

    public RegistroPedidosDTO registerPedido(BcpHeadPedido cabecera, List<BcpDetPedido> listaPedidos, Long contador) {

        List<BcpDetPedido> listaGuardados = new ArrayList<>();
        List<BcpDetPedido> listaNoGuardados = new ArrayList<>();
        RegistroPedidosDTO registroPedidosDTO = new RegistroPedidosDTO();

        registroPedidosDTO.setCabecera(cabeceraPedido.save(cabecera));
        AtomicInteger index = new AtomicInteger();

        listaPedidos.forEach(lp -> {
            var productoInventario = inventarioRepository
                    .findBcpInventarioByIdInventario(lp.getCodInventario()).orElse(null);
            if (!Objects.isNull(productoInventario) && productoInventario.getStock() > lp.getCantidad()) {
                index.getAndIncrement();
                lp.setCodFactura(contador);
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
        registroPedidosDTO.setListaPedidos(listaGuardados);
        registroPedidosDTO.setListaNoGuardados(listaNoGuardados);

        return registroPedidosDTO;
    }
}

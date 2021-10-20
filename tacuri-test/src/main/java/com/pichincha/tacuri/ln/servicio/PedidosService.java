package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.dto.PedidoDTO;
import com.pichincha.tacuri.ln.dto.PedidoFechaDTO;
import com.pichincha.tacuri.ln.dto.ProductoDTO;
import com.pichincha.tacuri.ln.entity.BcpDetPedido;
import com.pichincha.tacuri.ln.entity.BcpDetPedidoPK;
import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import com.pichincha.tacuri.ln.repositorio.BcpDetPedidoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpHeadPedidoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpInventarioRepository;
import com.pichincha.tacuri.util.BceConstant;
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
    public PedidoDTO save(ProductoDTO productoDTO) {
        PedidoDTO response;
        try {
            if (!productoDTO.getListaPedidos().isEmpty()) {
                Date fecha = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD)
                        .parse(productoDTO.getFecha());
                Integer contadorFact = cabeceraPedido.countAllPedidos() + 1;

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

    public List<PedidoDTO> findPedidosByIdClienteAndFecha(PedidoFechaDTO pedidoFechaDTO) {
        List<PedidoDTO> listaPedidosRecuperados = new ArrayList<>();
        try {
            Date fechaInicio = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(pedidoFechaDTO.getFechaInicio());
            Date fechaFin = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(pedidoFechaDTO.getFechaFin());

            List<BcpHeadPedido> listaCabeceraPedidos = cabeceraPedido
                    .findBcpHeadPedidoByIdClienteAndFecha(pedidoFechaDTO.getIdCliente(), fechaInicio, fechaFin);

            listaCabeceraPedidos.forEach(lp -> {
                PedidoDTO pedidosDTO = new PedidoDTO();
                pedidosDTO.setCabecera(lp);
                pedidosDTO.setListaPedidos(detallePedido.findBcpDetPedidoByCodFactura(lp.getCodFac())
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

    public PedidoDTO registerPedido(BcpHeadPedido cabecera, List<BcpDetPedido> listaPedidos, Long contador) {

        List<BcpDetPedido> listaGuardados = new ArrayList<>();
        List<BcpDetPedido> listaNoGuardados = new ArrayList<>();
        PedidoDTO registroPedidosDTO = new PedidoDTO();

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

package com.pichincha.tacuri.ln.gestor.consulta;

import com.pichincha.tacuri.ln.entity.BcpHeadPedido;
import com.pichincha.tacuri.ln.repositorio.BcpDetPedidoRepository;
import com.pichincha.tacuri.ln.repositorio.BcpHeadPedidoRepository;
import com.pichincha.tacuri.util.BceConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fmtacuri
 */
@Component
@Log4j2
public class PedidosConsultaGestor {

    @Autowired
    private BcpHeadPedidoRepository cabeceraPedido;

    @Autowired
    private BcpDetPedidoRepository detallePedido;

    public Integer contarFacturas() {
        return cabeceraPedido.contarFacturas();
    }

    public Map<String, Object> recuperarPedidosPorClienteAndFecha(Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        try{
            Date fechaInicio = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(body.get("fechaInicio").toString());
            Date fechaFin = new SimpleDateFormat(BceConstant.FORMAT_YYYY_MM_DD).parse(body.get("fechaFin").toString());
            String idCliente = body.get("idCliente").toString();

            List<BcpHeadPedido> listaCabeceraPedidos = cabeceraPedido
                    .buscarPedidosPorClienteAndFechas(idCliente, fechaInicio, fechaFin);

            listaCabeceraPedidos.forEach(lp -> {
                Map<String, Object> mapProveedor = new HashMap<>();
                mapProveedor.put("Pedido", lp);
                mapProveedor.put("DetallePedido", detallePedido.buscarPedidoById(lp.getCodFac()));
                response.put(lp.getCodFac().toString(), mapProveedor);
            });
        } catch (Exception e) {
            log.error("Error en al recuperarPedidosPorClienteAndFecha");
        }

        return response;
    }
}

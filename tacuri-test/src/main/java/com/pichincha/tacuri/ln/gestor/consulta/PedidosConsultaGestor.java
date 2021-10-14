package com.pichincha.tacuri.ln.gestor.consulta;

import com.pichincha.tacuri.ln.repositorio.BcpHeadPedidoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fmtacuri
 */
@Component
@Log4j2
public class PedidosConsultaGestor {

    @Autowired
    private BcpHeadPedidoRepository cabeceraPedido;

    public Integer contarFacturas() {
        return cabeceraPedido.contarFacturas();
    }
}

package com.pichincha.tacuri.ln.servicio;

import com.pichincha.tacuri.ln.gestor.almacenamiento.PedidoAlmacenamientoGestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author fmtacuri
 */
@Service
public class PedidosService {

    @Autowired
    private PedidoAlmacenamientoGestor pedidoAlmacenamientoGestor;

    @Transactional
    public Map<String, Object> registrarPedido(Map<String, Object> body) {
        return pedidoAlmacenamientoGestor.registrarPedido(body);
    }
}

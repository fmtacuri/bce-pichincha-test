package com.pichincha.tacuri.pedido;

import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.ln.servicio.PedidosService;
import com.pichincha.tacuri.rest.PedidoController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PedidoControllerTest extends MockitoFactory {

    @InjectMocks
    PedidoController controller;

    @Mock
    private PedidosService service;

    @Test
    void eliminarPedido() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.doNothing().when(service).eliminarPedido(1L);
        ResponseEntity<?> responseEntity = controller.eliminarPedido(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).eliminarPedido(1L);
    }
}
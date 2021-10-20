package com.pichincha.tacuri.pedido;

import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.ln.servicio.OrderService;
import com.pichincha.tacuri.rest.OrderController;
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
class OrderControllerTest extends MockitoFactory {

    @InjectMocks
    OrderController controller;

    @Mock
    private OrderService service;

    @Test
    void eliminarPedido() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.doNothing().when(service).deleteOrder(1L);
        ResponseEntity<?> responseEntity = controller.deleteOrder(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).deleteOrder(1L);
    }
}
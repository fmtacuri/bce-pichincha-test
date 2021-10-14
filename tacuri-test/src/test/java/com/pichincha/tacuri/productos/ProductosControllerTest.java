package com.pichincha.tacuri.productos;

import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.ln.servicio.BcpProductoService;
import com.pichincha.tacuri.rest.BcpProductoController;
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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ProductosControllerTest extends MockitoFactory {

    @InjectMocks
    BcpProductoController controller;

    @Mock
    private BcpProductoService service;

    @Test
    void registrarProductoProveedor() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Map<String, Object> body = new HashMap<>();
        body.put("idInventario", "A1P1_TEST");
        body.put("stock", 9000);
        body.put("precio", 600);
        body.put("codProducto", "A1");
        body.put("codProveedor", 1);

        var listaProd = service.buscarAllInventario();

        Mockito.when(service.registrarProductoProveedor(body)).thenReturn(listaProd);
        ResponseEntity<?> responseEntity = controller.registrarProductoProveedor(body);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).registrarProductoProveedor(body);
    }

    @Test
    void actualizarProducto() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Map<String, Object> body = new HashMap<>();
        body.put("descripcion", "EDIT PODUCTOS");
        body.put("codProducto", "A1");

        var listaProd = service.buscarAllProductos();

        Mockito.when(service.actualizarProducto(body)).thenReturn(listaProd);
        ResponseEntity<?> responseEntity = controller.actualizarProducto(body);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).actualizarProducto(body);
    }
}
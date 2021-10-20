package com.pichincha.tacuri.productos;

import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.servicio.ProductService;
import com.pichincha.tacuri.rest.ProductController;
import com.pichincha.tacuri.util.JsonUtils;
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
class ProductControllerTest extends MockitoFactory {

    @InjectMocks
    ProductController controller;

    @Mock
    private ProductService service;

    @Test
    void saveBcpInventario() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Map<String, Object> body = new HashMap<>();
        body.put("idInventario", "A1P1_TEST");
        body.put("stock", 9000);
        body.put("precio", 600);
        body.put("codProducto", "A1");
        body.put("codProveedor", 1);

        BcpInventario inventario = JsonUtils.mapToObject(body, BcpInventario.class);

        Mockito.when(service.saveBcpInventary(inventario)).thenReturn(inventario);
        ResponseEntity<?> responseEntity = controller.saveBcpInventary(inventario);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).saveBcpInventary(inventario);
    }

    @Test
    void updateBcpProducto() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Map<String, Object> body = new HashMap<>();
        body.put("descripcion", "EDIT PODUCTOS");
        body.put("codProducto", "A1");

        BcpProducto producto = JsonUtils.mapToObject(body, BcpProducto.class);

        Mockito.when(service.updateProduct(producto)).thenReturn(producto);
        ResponseEntity<?> responseEntity = controller.updateProduct(producto);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).updateProduct(producto);
    }
}
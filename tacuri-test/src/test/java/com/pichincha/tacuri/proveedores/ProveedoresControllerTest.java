package com.pichincha.tacuri.proveedores;

import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.servicio.BcpProveedorService;
import com.pichincha.tacuri.rest.BcpProveedorController;
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
class ProveedoresControllerTest extends MockitoFactory {

    @InjectMocks
    BcpProveedorController controller;

    @Mock
    private BcpProveedorService service;

    @Test
    void saveBcpProveedor() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Map<String, Object> body = new HashMap<>();
        body.put("codProveedor", 150);
        body.put("nombre", "TEST");
        body.put("direccion", "TEST_DIR");
        body.put("telefono", "TEST_TLF");
        body.put("nombreEmpresa", "TEST_EMP");

        BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);

        Mockito.when(service.saveBcpProveedor(body)).thenReturn(proveedor);
        ResponseEntity<?> responseEntity = controller.saveBcpProveedor(body);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).saveBcpProveedor(body);
    }

    @Test
    void updateBcpProveedor() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Map<String, Object> body = new HashMap<>();
        body.put("codProveedor", 150);
        body.put("nombre", "TEST_EDIT");
        body.put("direccion", "TEST_DIR");
        body.put("telefono", "TEST_TLF");
        body.put("nombreEmpresa", "TEST_EMP");

        BcpProveedor proveedor = JsonUtils.mapToObject(body, BcpProveedor.class);

        Mockito.when(service.updateBcpProveedor(body)).thenReturn(proveedor);
        ResponseEntity<?> responseEntity = controller.updateBcpProveedor(body);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).updateBcpProveedor(body);
    }
}
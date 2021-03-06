package com.pichincha.tacuri.unitario;

import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.service.ProviderServiceImpl;
import com.pichincha.tacuri.rest.ProviderController;
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
class ProviderControllerTest extends MockitoFactory {

    @InjectMocks
    ProviderController controller;

    @Mock
    private ProviderServiceImpl service;

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

        Mockito.when(service.saveBcpProveedor(proveedor)).thenReturn(proveedor);
        ResponseEntity<?> responseEntity = controller.saveBcpProveedor(proveedor);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).saveBcpProveedor(proveedor);
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

        Mockito.when(service.updateBcpProveedor(proveedor)).thenReturn(proveedor);
        ResponseEntity<?> responseEntity = controller.updateBcpProveedor(proveedor);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Mockito.verify(service).updateBcpProveedor(proveedor);
    }
}
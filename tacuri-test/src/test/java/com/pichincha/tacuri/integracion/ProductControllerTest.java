package com.pichincha.tacuri.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.tacuri.MockitoFactory;
import com.pichincha.tacuri.exceptions.CustomException;
import com.pichincha.tacuri.ln.dto.ProviderDTO;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.repository.proyectios.ProductProyection;
import com.pichincha.tacuri.ln.service.ProductServiceImpl;
import com.pichincha.tacuri.rest.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
class ProductControllerTest extends MockitoFactory {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductServiceImpl service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void initialData() {
        Mockito.reset(service);
    }

    private BcpInventario setInventory() {
        BcpInventario inventario = new BcpInventario();
        inventario.setIdInventario("A1P1_TEST");
        inventario.setStock(6000);
        inventario.setPrecio(60.00F);
        inventario.setCodProducto("A31");
        inventario.setCodProveedor(1L);

        return inventario;
    }

    private BcpProducto setProduct() {
        BcpProducto product = new BcpProducto();
        product.setCodProducto("A55");
        product.setDescripcion("TEST REGISTER");

        return product;
    }

    private BcpProveedor setProvider() {
        BcpProveedor product = new BcpProveedor();
        product.setCodProveedor(10L);
        product.setNombre("Freddy");
        product.setDireccion("Dir");
        product.setTelefono("Tel");
        product.setNombreEmpresa("TEST");

        return product;
    }

    private List<ProviderDTO> setPedidosObject() {
        List<ProviderDTO> listaProviders = new ArrayList<>();
        ProviderDTO providerDTO = new ProviderDTO();
        providerDTO.setProveedor(setProvider());
        providerDTO.setListaProductos(setListaProductos());

        return listaProviders;
    }

    private List<ProductProyection> setListaProductos() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        ProductProyection projection = factory.createProjection(ProductProyection.class);
        projection.setCodProducto("A1");
        projection.setStock(5);
        projection.setDescripcion("DESC");
        projection.setPrecio(450F);
        List<ProductProyection> productos = new ArrayList<>();
        productos.add(projection);

        return productos;
    }

    private void mockSaveBcpInventario() {
        given(service.saveBcpInventary(this.setInventory())).willReturn(this.setInventory());
    }

    private void mockSaveBcpProduct() {
        given(service.saveBcpProduct(this.setProduct())).willReturn(this.setProduct());
    }

    private void mockUpdateProduct() {
        given(service.updateProduct(this.setProduct())).willReturn(this.setProduct());
    }

    private void mockSaveBcpProductGenerateError() {
        BcpProducto product = this.setProduct();
        product.setCodProducto("A31");
        given(service.saveBcpProduct(product)).willThrow(new CustomException("Error en saveBcpProducto"));
    }

    private void mockFindProductosByStockAndProveedor() {
        List<ProviderDTO> listaProviders = setPedidosObject();
        given(service.findProductosByStockAndProveedor()).willReturn(listaProviders);
    }

    @Test
    void saveBcpInventary() throws Exception {
        mockSaveBcpInventario();
        mockMvc.perform(post("/api/producto/save-product-provider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.setInventory()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("idInventario", is("A1P1_TEST")))
                .andExpect(jsonPath("stock", is(6000)));
        verify(service, times(1)).saveBcpInventary(this.setInventory());
    }

    @Test
    void saveBcpProduct() throws Exception {
        mockSaveBcpProduct();
        mockMvc.perform(post("/api/producto/save-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.setProduct()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("codProducto", is("A55")))
                .andExpect(jsonPath("descripcion", is("TEST REGISTER")));
        verify(service, times(1)).saveBcpProduct(this.setProduct());
    }

    @Test
    void updateProduct() throws Exception {
        mockUpdateProduct();
        mockMvc.perform(put("/api/producto/update-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.setProduct()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("codProducto", is("A55")))
                .andExpect(jsonPath("descripcion", is("TEST REGISTER")));
        verify(service, times(1)).updateProduct(this.setProduct());
    }

    @Test
    void findProductosByStockAndProveedor() throws Exception {
        mockFindProductosByStockAndProveedor();
        mockMvc.perform(get("/api/producto/find-provider-product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].proveedor", is(setProvider())))
                .andExpect(jsonPath("$[0].listaProductos", is(setListaProductos())));

        verify(service, times(1)).findProductosByStockAndProveedor();
    }

    @Test
    void saveBcpProductGenerateError() throws Exception {
        mockSaveBcpProductGenerateError();
        BcpProducto product = this.setProduct();
        product.setCodProducto("A31");
        mockMvc.perform(post("/api/producto/save-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        verify(service, times(1)).saveBcpProduct(this.setProduct());
    }
}
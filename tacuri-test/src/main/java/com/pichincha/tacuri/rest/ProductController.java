package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.dto.ProviderDTO;
import com.pichincha.tacuri.ln.entity.BcpInventario;
import com.pichincha.tacuri.ln.entity.BcpProducto;
import com.pichincha.tacuri.ln.service.ProductService;
import com.pichincha.tacuri.ln.service.ProductServiceImpl;
import com.pichincha.tacuri.util.BceConstant;
import com.pichincha.tacuri.util.factory.BeanFactory;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/producto")
@RequiredArgsConstructor
public class ProductController {

    private ProductService service = BeanFactory.getBean(ProductServiceImpl.class);

    @PostMapping("/save-product")
    public ResponseEntity<BcpProducto> saveBcpProduct(@NotNull @RequestBody BcpProducto producto) {
        ResponseEntity response;
        var requestValue = service.saveBcpProduct(producto);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @PostMapping("/save-product-provider")
    public ResponseEntity<BcpInventario> saveBcpInventary(@NotNull @RequestBody BcpInventario inventario) {
        ResponseEntity response;
        var requestValue = service.saveBcpInventary(inventario);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping("/find-provider-product")
    public ResponseEntity<List<ProviderDTO>> findProductosByStockAndProveedor() {
        return new ResponseEntity<>(service.findProductosByStockAndProveedor(), HttpStatus.OK);
    }

    @PutMapping("/update-product")
    public ResponseEntity<BcpProducto> updateProduct(@NotNull @RequestBody BcpProducto producto) {
        return new ResponseEntity<>(service.updateProduct(producto), HttpStatus.OK);
    }
}
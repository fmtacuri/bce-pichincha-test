package com.pichincha.tacuri.rest;

import com.pichincha.tacuri.ln.entity.BcpProveedor;
import com.pichincha.tacuri.ln.service.ProviderService;
import com.pichincha.tacuri.ln.service.ProviderServiceImpl;
import com.pichincha.tacuri.util.BceConstant;
import com.pichincha.tacuri.util.factory.BeanFactory;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author fmtacuri
 * @version 1.1
 */
@RestController
@RequestMapping(BceConstant.URL_PATH + "/proveedor")
@RequiredArgsConstructor
public class ProviderController {

    //    private ProviderService service = BeanFactory.getBean(ProviderServiceImpl.class);
    private final ProviderService service = new ProviderServiceImpl();


    @GetMapping("/{codigo}")
    public ResponseEntity<BcpProveedor> findBcpProveedorByCodProveedor(@NotNull @PathVariable("codigo") Long codigo) {
        ResponseEntity response;
        var requestValue = service.findBcpProveedorByCodProveedor(codigo);
        if (Objects.isNull(requestValue)) {
            response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(requestValue, HttpStatus.OK);
        }

        return response;
    }

    @PostMapping("/guardar-proveedor")
    public ResponseEntity<BcpProveedor> saveBcpProveedor(@NotNull @RequestBody BcpProveedor proveedor) {
        return new ResponseEntity<>(service.saveBcpProveedor(proveedor), HttpStatus.OK);
    }

    @PutMapping("/actualizar-proveedor")
    public ResponseEntity<BcpProveedor> updateBcpProveedor(@NotNull @RequestBody BcpProveedor proveedor) {
        return new ResponseEntity<>(service.updateBcpProveedor(proveedor), HttpStatus.OK);
    }
}
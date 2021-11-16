package com.pichincha.tacuri.rest.exchange;

import com.pichincha.tacuri.ln.dto.ResponseDto;
import com.pichincha.tacuri.ln.service.exchange.PokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poke")
public class PokeController {

  @Autowired
  PokeService pokeService;

  @PostMapping("/get-poke-response")
  ResponseEntity<ResponseDto> getPokeResponse() {
    return new ResponseEntity<>(pokeService.getPokeResponse(), HttpStatus.OK);
  }

}

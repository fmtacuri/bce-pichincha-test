package com.pichincha.tacuri.ln.service.exchange;

import com.pichincha.tacuri.ln.dto.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class IPokeResponse implements PokeService {

  @Autowired
  RestTemplate restTemplate;

  @Override
  public ResponseDto getPokeResponse() {
    ResponseDto responseDto = new ResponseDto();
    String response = "";
    try {
      ResponseEntity<String> responseEntity = restTemplate.exchange("https://pokeapi.co/api/v2/pokemon/ditto1",
          HttpMethod.GET, null, String.class, response);
      log.info(responseEntity.getBody());
      responseDto.setMessage("OK");
    } catch (RestClientException e) {
      log.error(e.getMessage());
    }

    return responseDto;
  }
}

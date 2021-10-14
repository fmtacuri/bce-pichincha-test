package com.pichincha.tacuri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author fmtacuri
 */
public class MockitoFactory {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Map<String, Object> map = new HashMap<>();
    private static ObjectNode objectNode = null;

    protected ObjectNode getObjectNode() {
        if (Objects.isNull(objectNode)) {
            objectNode = mapper.createObjectNode();
        }
        return objectNode;
    }

    protected ObjectMapper getMapper() {
        return mapper;
    }

    protected Map<String, Object> getMap() {
        return map;
    }
}

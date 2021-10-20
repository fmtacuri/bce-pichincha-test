package com.pichincha.tacuri.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author fmtacuri
 * @version 1.1
 */
@Data
@Builder
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}

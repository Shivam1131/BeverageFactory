package com.bf.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author Sadashiv Kadam
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceResponseBean {

    private String status;
    private String message;
    private HttpStatus httpStatus;
    private String totalCost;
    private Map<String, Float> individualPrices;

}

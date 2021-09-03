package org.paybook.com.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@ToString
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = false)
public class ChargeDto {

    @JsonProperty("user")
    private String user;

    @JsonProperty("book")
    private String book;

    @JsonProperty("charge")
    private String charge;

}

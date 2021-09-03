package org.paybook.com.controller.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ChargeDtoTest {

    @Test
    void deserializeTest() throws JsonProcessingException {
        String json = "{\n" +
                "  \"user_id\": \"9lSbTlfCh3UawuxgGS63F9zNAJw2\",\n" +
                "  \"book_id\": \"FMF92AK0AA7WNMLFV5Ol\",\n" +
                "  \"charge_id\": \"5X00Y2G3KCIY9D2VP4H9\"\n" +
                "}";

        ChargeDto chargeDto = new ObjectMapper().readValue(json, ChargeDto.class);
        System.out.println(chargeDto);
    }
}
package org.paybook.com.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.paybook.com.ValidationGroups;
import org.paybook.com.services.Destinatario;
import org.paybook.com.services.cobranca.EnumStatusCobranca;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;

@Getter
@SuperBuilder
public class CobrancaDto implements DtoObject {

    @JsonProperty("id_cobranca")
    @Null(message = "id_cobranca must be null", groups = ValidationGroups.Post.class)
    @NotBlank(message = "id_cobranca may not be blank", groups = ValidationGroups.Put.class)
    private String idCobranca;

    @NotBlank(message = "id_book may not be blank")
    @JsonProperty("id_book")
    private String idBook;

    @NotNull(message = "destinatario may not be blank")
    private Destinatario destinatario;

    @NotNull(message = "valor may not be blank")
    private Integer valor;

    @NotNull(message = "data_vencimento may not be blank")
    @Future
    @JsonProperty("data_vencimento")
    private Instant dataVencimento;

    @NotNull(message = "status may not be blank", groups = ValidationGroups.Path.class)
    private EnumStatusCobranca status;

    @Builder.Default
    private String descricao = "not_informed";
}

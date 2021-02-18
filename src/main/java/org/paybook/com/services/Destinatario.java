package org.paybook.com.services;

import lombok.*;
import lombok.experimental.NonFinal;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Destinatario {

    @NonFinal
    @With
    private String email;

    @NonFinal
    @With
    private String nome;

    @NonFinal
    @With
    private String telefone;

}

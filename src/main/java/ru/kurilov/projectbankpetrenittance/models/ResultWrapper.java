package ru.kurilov.projectbankpetrenittance.models;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultWrapper {
    private LogPas logPas;
    private DataUsers dataUsers;
    private CreditCarts creditCarts;
}

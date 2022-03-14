package ru.kurilov.projectbankpetrenittance.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
public class CreditCarts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long numberCart;
    private long balance;

    @ManyToOne
    private DataUsers dataUsers;


    public CreditCarts() {

    }
}

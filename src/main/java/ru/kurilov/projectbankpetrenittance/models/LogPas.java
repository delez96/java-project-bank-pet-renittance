package ru.kurilov.projectbankpetrenittance.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@ToString
public class LogPas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotEmpty(message = "Введите логин")
    private String login;

    @Size(min = 5, message = "Пароль должен содержать больше 2-ух символов")
    private String password;

    private String role = "USER";

    public LogPas(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LogPas() {

    }
}

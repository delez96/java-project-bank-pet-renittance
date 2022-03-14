package ru.kurilov.projectbankpetrenittance.models;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String secondName;
    private String lastName;

    @OneToOne
    private LogPas logPas;

    @OneToMany(mappedBy = "dataUsers", fetch = FetchType.EAGER)
    private List<CreditCarts> creditCartsList = new ArrayList<>();


    public long sum(){
        if (creditCartsList.size() != 0){
            return creditCartsList.stream().map(o -> o.getBalance()).reduce((a,b) -> a + b).orElse(null);
        }else return 0;
    }

}

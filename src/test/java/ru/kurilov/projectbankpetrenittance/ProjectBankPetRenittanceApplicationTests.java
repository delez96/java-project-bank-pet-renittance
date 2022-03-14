package ru.kurilov.projectbankpetrenittance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.kurilov.projectbankpetrenittance.models.CreditCarts;
import ru.kurilov.projectbankpetrenittance.models.DataUsers;
import ru.kurilov.projectbankpetrenittance.models.LogPas;
import ru.kurilov.projectbankpetrenittance.models.ResultWrapper;
import ru.kurilov.projectbankpetrenittance.repo.CreditCartsRepo;
import ru.kurilov.projectbankpetrenittance.repo.DataUsersRepo;
import ru.kurilov.projectbankpetrenittance.repo.LogPasRepo;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProjectBankPetRenittanceApplicationTests {
    @Autowired
    LogPasRepo logPasRepo;
    @Autowired
    DataUsersRepo dataUsersRepo;
    @Autowired
    CreditCartsRepo creditCartsRepo;

    @Test
    @Transactional
    void contextLoads() {
        LogPas logPas = new LogPas("u1", "u1");


        DataUsers dataUsers = new DataUsers();
        dataUsers.setFirstName("av");
        dataUsers.setLastName("av");
        dataUsers.setSecondName("av");
        dataUsers.setLogPas(logPas);

//        CreditCarts creditCarts = new CreditCarts();
//        creditCarts.setBalance(200);
//        creditCarts.setNumberCart(1111);
//        creditCarts.setDataUsers(dataUsers);
//
//        List<CreditCarts> creditCartsList = new ArrayList<>();
//        creditCartsList.add(creditCarts);
//
//        dataUsers.setCreditCartsList(creditCartsList);

        logPasRepo.save(logPas);
        dataUsersRepo.save(dataUsers);
//        creditCartsRepo.save(creditCarts);

//        System.out.println(dataUsersRepo.getById(dataUsers.getId()));
        System.out.println("+++++++++++++++");

    }

}

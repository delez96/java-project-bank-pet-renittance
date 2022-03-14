package ru.kurilov.projectbankpetrenittance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kurilov.projectbankpetrenittance.models.LogPas;
import ru.kurilov.projectbankpetrenittance.models.ResultWrapper;

import java.util.List;


@Repository
public interface LogPasRepo extends JpaRepository<LogPas, Long> {
    LogPas findByLogin(String login);

    LogPas getByLogin(String currentUserName);


}

package ru.kurilov.projectbankpetrenittance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurilov.projectbankpetrenittance.models.DataUsers;
import ru.kurilov.projectbankpetrenittance.models.LogPas;

public interface DataUsersRepo extends JpaRepository<DataUsers, Long> {
    DataUsers getByLogPas(LogPas logPas);
}

package ru.kurilov.projectbankpetrenittance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.kurilov.projectbankpetrenittance.models.CreditCarts;

import java.util.List;

public interface CreditCartsRepo extends JpaRepository<CreditCarts, Long> {
    List<CreditCarts> getByDataUsersId(Long id);

    List<CreditCarts> findAllByNumberCart(long numberCart);
    CreditCarts getByNumberCart(long numberCart);

    @Modifying
    @Query(value = "update credit_carts set balance = ? where number_cart = ?", nativeQuery = true)
    int setBalanceToCart(Long balance, Long numberCart);

}

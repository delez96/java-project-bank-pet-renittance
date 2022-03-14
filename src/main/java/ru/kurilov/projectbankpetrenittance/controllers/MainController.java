package ru.kurilov.projectbankpetrenittance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kurilov.projectbankpetrenittance.models.CreditCarts;
import ru.kurilov.projectbankpetrenittance.models.DataUsers;
import ru.kurilov.projectbankpetrenittance.models.LogPas;
import ru.kurilov.projectbankpetrenittance.repo.CreditCartsRepo;
import ru.kurilov.projectbankpetrenittance.repo.DataUsersRepo;
import ru.kurilov.projectbankpetrenittance.repo.LogPasRepo;
import ru.kurilov.projectbankpetrenittance.service.ExchangeRate;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private LogPasRepo logPasRepo;
    @Autowired
    private DataUsersRepo dataUsersRepo;
    @Autowired
    private CreditCartsRepo creditCartsRepo;

    @GetMapping("/")
    public String hello() {
        return "redirect:/info/rub";
    }
    @ModelAttribute("dollar")
    public double getDollar(){
        return ExchangeRate.getDollar();
    }

    @ModelAttribute("euro")
    public double getEuro(){
        return ExchangeRate.getEuro();
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/convert")
    public String converter() {
        return "converter";
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable(value = "id") String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            LogPas logPas = logPasRepo.getByLogin(currentUserName);
            DataUsers dataUsers = dataUsersRepo.getById(logPas.getId());
            List<CreditCarts> creditCartsList = creditCartsRepo.getByDataUsersId(dataUsers.getId());
            model.addAttribute("logPas", logPas);
            model.addAttribute("dataUsers", dataUsers);
            model.addAttribute("creditCartsList", creditCartsList);
            int val;
            double course;
            if (id.equals("rub")){
                val = 0;
                course = 1;
            }else if (id.equals("usd")){
                val = 1;
                course = getDollar();
            }else {
                val = 2;
                course = getEuro();
            }

            model.addAttribute("value", val);
            model.addAttribute("course", course);

            return "info";
        } else
            return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("logPas", new LogPas());
        return "registration";
    }

    @PostMapping("/registration")
    @Transactional
    public String save(@ModelAttribute("logPas") @Valid LogPas logPas, @RequestParam String passw,
                       BindingResult bindingResult, Model model) {
        if (logPasRepo.findByLogin(logPas.getLogin()) != null) {
            bindingResult.addError(new FieldError("err", "login", "Данный пользователь уже существует"));
        }
        if (!logPas.getPassword().equals(passw)) {
            bindingResult.addError(new FieldError("err", "password", "Пароли не совподают"));

        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        logPasRepo.save(logPas);
        DataUsers dataUsers = new DataUsers();

        dataUsers.setLogPas(logPas);
        model.addAttribute("dataUsers", dataUsers);
        return "registration2";
    }


    @PostMapping("/registration2")
    public String save2(@ModelAttribute("dataUsers") DataUsers dataUsers) {
        dataUsersRepo.save(dataUsers);
        return "redirect:/info/rub";
    }

    @GetMapping("/addCart")
    public String addCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            LogPas logPas = logPasRepo.getByLogin(currentUserName);
            DataUsers dataUsers = dataUsersRepo.getByLogPas(logPas);
            model.addAttribute("dataUsers", dataUsers);
            CreditCarts creditCarts = new CreditCarts();
            creditCarts.setDataUsers(dataUsers);
            model.addAttribute("creditCart", creditCarts);
            return "addCart";

        } else return "redirect:/login";
    }

    @PostMapping("/addCart")
    @Transactional
    public String addCreditCart(@ModelAttribute("creditCart") @Valid CreditCarts creditCarts, BindingResult bindingResult,
                                Model model) {
        if (creditCartsRepo.findAllByNumberCart(creditCarts.getNumberCart()).size() == 1) {
            bindingResult.addError(new FieldError("err", "numberCart", "Данная карта уже существует"));
        }
        if (bindingResult.hasErrors()){
            return "addCart";
        }
        creditCarts.setBalance((long) (Math.random() * 1500));
        creditCartsRepo.save(creditCarts);
        return "redirect:/info/rub";
    }

    @GetMapping("/bankTransaction")
    public String bankTrans(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            LogPas logPas = logPasRepo.getByLogin(currentUserName);
            DataUsers dataUsers = dataUsersRepo.getByLogPas(logPas);
            model.addAttribute("dataUsers", dataUsers);
            return "bankTransaction";

        } else return "redirect:/login";
    }

    @PostMapping("/bankTransaction")
    @Transactional
    public String transaction(Model model,
                              @RequestParam Long fromCart, @RequestParam Long money, @RequestParam Long toCart){
        boolean flag = false;
        if (creditCartsRepo.findAllByNumberCart(toCart).size() == 0) {
            flag = true;
            model.addAttribute("messageCart", "Карта не найдена");
        }
        Long balance = creditCartsRepo.getByNumberCart(fromCart).getBalance();
        if (balance < money) {
            flag = true;
            model.addAttribute("messageMoney", String.format("Введенная сумма превышает баланс на карте.\nНа карте № %d баланс %d", fromCart, balance));
        }else if (money <= 0) {
            flag = true;
            model.addAttribute("messageMoney", "Введите сумму больше 0");
        }
        if (flag){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            LogPas logPas = logPasRepo.getByLogin(currentUserName);
            DataUsers dataUsers = dataUsersRepo.getByLogPas(logPas);
            model.addAttribute("dataUsers", dataUsers);

            return "bankTransaction";
        }

        long balanceToCartBefore = creditCartsRepo.getByNumberCart(toCart).getBalance();
        long balanceFromCartBefore = creditCartsRepo.getByNumberCart(fromCart).getBalance();

        creditCartsRepo.setBalanceToCart(balanceFromCartBefore - money, fromCart);
        creditCartsRepo.setBalanceToCart(balanceToCartBefore + money, toCart);

        return "redirect:/info/rub";
    }
}


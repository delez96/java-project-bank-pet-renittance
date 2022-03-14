package ru.kurilov.projectbankpetrenittance.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kurilov.projectbankpetrenittance.repo.LogPasRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private LogPasRepo dao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LogPas myUser= dao.findByLogin(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        UserDetails user = User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }
}
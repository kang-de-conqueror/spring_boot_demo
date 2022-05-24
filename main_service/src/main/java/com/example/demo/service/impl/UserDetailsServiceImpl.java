package com.example.demo.service.impl;

import com.example.demo.dto.AccountUser;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    // Repository
    private final AccountRepository accountRepository;

    @SneakyThrows
    @Override
    public AccountUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.getByUsername(username);
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new AccountUser(optionalAccount.get());
    }
}

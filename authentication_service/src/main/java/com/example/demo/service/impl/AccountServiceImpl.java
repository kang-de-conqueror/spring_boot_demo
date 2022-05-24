package com.example.demo.service.impl;

import com.example.demo.constant.Constant;
import com.example.demo.constant.Status;
import com.example.demo.dto.BaseResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.ApiException;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import com.example.demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    // Service
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Repository
    private final AccountRepository accountRepository;

    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseResponse login(LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        LoginResponse loginResponse = (LoginResponse) userDetailsService.loadUserByUsername(loginRequest.getUsername());

        final String token = jwtService.generateToken(loginResponse.getUsername());
        loginResponse.setToken(token);

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource), loginResponse);
    }

    @Override
    @Transactional
    public BaseResponse register(RegisterRequest registerRequest) {
        Account account = new Account();

        account.setFullName(registerRequest.getFullname());
        account.setUsername(registerRequest.getUsername());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setIsActive(Boolean.TRUE);
        account.setRoleId(Constant.MEMBER);

        try {

            accountRepository.save(account);
        } catch (Exception ex) {
            log.error("Register account catch exception: {}", ex.getMessage());
            throw new ApiException(Status.ACCOUNT_DUPLICATED);
        }

        return new BaseResponse(Status.SUCCESS, Status.getMessage(Status.SUCCESS, null, messageSource));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException de) {
            log.error("Authenticate catch exception: {}", de.getMessage());
            throw new Exception("USER_DISABLED", de);
        } catch (BadCredentialsException bce) {
            log.error("Authenticate catch exception: {}", bce.getMessage());
            throw new ApiException(Status.ACCOUNT_LOGIN_FAIL);
        }
    }
}

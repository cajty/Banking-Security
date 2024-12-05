package org.ably.bankingsecurity.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.bankingsecurity.domain.dto.AccountDTO;
import org.ably.bankingsecurity.domain.entities.Account;
import org.ably.bankingsecurity.domain.entities.User;
import org.ably.bankingsecurity.domain.enums.AccountStatus;
import org.ably.bankingsecurity.domain.request.AccountRequest;
import org.ably.bankingsecurity.exception.account.AccountNotFoundException;
import org.ably.bankingsecurity.exception.account.InsufficientBalanceException;
import org.ably.bankingsecurity.mapper.AccountMapper;
import org.ably.bankingsecurity.repository.AccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserService userService;

    @Transactional
    public AccountDTO save(AccountRequest request) {

        Account account = accountMapper.toEntity(request);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        account.setUser(
                userService.findByEmail(username)
        );


        account.setStatus(AccountStatus.ACTIVE);

        Account savedAccount = accountRepository.save(account);


        return accountMapper.toDTO(savedAccount);
    }

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll ();
    }


    public Account findById(UUID id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException("Account not found with id " + id)
        );

    }

    public List<Account> findByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Transactional
    public void delete(UUID id) {

        Account account = findById(id);

        if (account.getBalance() > 0) {
            throw new InsufficientBalanceException("Cannot delete account with non-zero balance");
        }

        accountRepository.deleteById(id);

    }

@Transactional
public void transferMoney(UUID senderAccountId, UUID receiverAccountId, double amount) {
        Account senderAccount = findById(senderAccountId);
        Account receiverAccount = findById(receiverAccountId);


    if (senderAccount.getBalance() < amount) {
        throw new InsufficientBalanceException("Insufficient funds in sender account");
    }

    senderAccount.setBalance(senderAccount.getBalance() - amount);
    receiverAccount.setBalance(receiverAccount.getBalance() + amount);

    accountRepository.save(senderAccount);
    accountRepository.save(receiverAccount);
}





}
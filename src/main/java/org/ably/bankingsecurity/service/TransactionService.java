package org.ably.bankingsecurity.service;


import lombok.RequiredArgsConstructor;
import org.ably.bankingsecurity.domain.documents.TransactionDocument;
import org.ably.bankingsecurity.domain.dto.TransactionDTO;
import org.ably.bankingsecurity.domain.entities.Transaction;
import org.ably.bankingsecurity.domain.enums.TransactionType;
import org.ably.bankingsecurity.domain.request.TransactionRequest;
import org.ably.bankingsecurity.exception.transaction.TransactionNotFoundException;
import org.ably.bankingsecurity.mapper.TransactionMapper;
import org.ably.bankingsecurity.repository.TransactionRepository;
import org.ably.bankingsecurity.repository.TransactionSearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;


    @Transactional
    public Transaction save(TransactionRequest request) {

        Transaction transaction = transactionMapper.toEntity(request);
        if ( request.getAmount() >= 10000) {
            transaction.setType(TransactionType.PENDING);
        }else {
            accountService.transferMoney(request.getAccountSenderId(), request.getAccountReceiverId(), request.getAmount());
            transaction.setType(TransactionType.COMPLETED);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);


        TransactionDocument document = TransactionDocument.fromEntity(savedTransaction);


        transactionSearchRepository.save(document);

        return savedTransaction;
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        return transactionMapper.toDTOList(transactionRepository.findAll());
    }
    @Transactional
    public List<TransactionDTO> findByUserId(Long userId) {
        return transactionMapper.toDTOList(transactionRepository.findByUserId(userId));
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        transactionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + id));
    }

    public List<TransactionDocument> searchTransactionsByAmountRange(Double minAmount, Double maxAmount) {
        return transactionSearchRepository.findByAmountBetween(minAmount, maxAmount);
    }

    public List<TransactionDocument> searchTransactionsByType(TransactionType type) {
        return transactionSearchRepository.findByType(type);
    }

    public List<TransactionDocument> searchTransactionsByUser(Long userId) {
        String userIdStr = userId.toString();
        return transactionSearchRepository.findBySenderUserIdOrReceiverUserId(userIdStr, userIdStr);
    }


}
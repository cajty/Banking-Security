package org.ably.bankingsecurity.mapper;

import org.ably.bankingsecurity.domain.dto.TransactionDTO;
import org.ably.bankingsecurity.domain.entities.Transaction;
import org.ably.bankingsecurity.domain.request.TransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {



    @Mapping(target = "accountOfSender", source = "accountOfSender.id")
    @Mapping(target = "accountOfReceiver", source = "accountOfSender.id")
    @Mapping(target = "type", source = "type")
    TransactionDTO toDTO(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountOfSender.id", source = "accountSenderId")
    @Mapping(target = "accountOfReceiver.id", source = "accountReceiverId")
    Transaction toEntity(TransactionRequest transactionRequest);

    List<TransactionDTO> toDTOList(List<Transaction> transactions);
}
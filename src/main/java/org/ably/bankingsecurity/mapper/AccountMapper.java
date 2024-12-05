package org.ably.bankingsecurity.mapper;

import org.ably.bankingsecurity.domain.dto.AccountDTO;
import org.ably.bankingsecurity.domain.entities.Account;
import org.ably.bankingsecurity.domain.request.AccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {



@Mapping(target = "accountNumber", source = "id")
    AccountDTO toDTO(Account account);



    Account toEntity(AccountRequest accountRequest);

    List<AccountDTO> toDTOList(List<Account> accounts);

}


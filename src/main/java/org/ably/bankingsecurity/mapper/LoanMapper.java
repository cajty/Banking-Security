package org.ably.bankingsecurity.mapper;

import org.ably.bankingsecurity.domain.dto.LoanDTO;
import org.ably.bankingsecurity.domain.entities.Loan;
import org.ably.bankingsecurity.domain.request.LoanRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface LoanMapper {


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "amount", source = "principal")
    @Mapping(target = "monthlyPayment", source = "monthlyPayment")
    @Mapping(target = "termMonths", source = "termMonths")
    @Mapping(target = "approved", source = "approved")
    LoanDTO toDTO(Loan loan);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "principal", source = "amount")
    @Mapping(target = "monthlyPayment", source = "mouthlyPayment")
    Loan toEntity(LoanRequest loanRequest);

    List<LoanDTO> toDTOList(List<Loan> loans);

}

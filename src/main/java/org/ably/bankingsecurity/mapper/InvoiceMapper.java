package org.ably.bankingsecurity.mapper;

import org.ably.bankingsecurity.domain.dto.InvoiceDTO;
import org.ably.bankingsecurity.domain.entities.Invoice;
import org.ably.bankingsecurity.domain.request.InvoiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {



    @Mapping(target = "userId", source = "user.id")
    InvoiceDTO toDTO(Invoice invoice);



    Invoice toEntity(InvoiceRequest invoiceRequest);

    List<InvoiceDTO> toDTOList(List<Invoice> bills);

}

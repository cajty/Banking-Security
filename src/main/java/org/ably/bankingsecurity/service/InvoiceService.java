package org.ably.bankingsecurity.service;


import lombok.AllArgsConstructor;
import org.ably.bankingsecurity.domain.entities.Invoice;
import org.ably.bankingsecurity.domain.request.InvoiceRequest;
import org.ably.bankingsecurity.mapper.InvoiceMapper;
import org.ably.bankingsecurity.repository.InvoiceRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final UserService userService;

    public Invoice save(InvoiceRequest invoiceRequest) {
        Invoice invoice = invoiceMapper.toEntity(invoiceRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        invoice.setUser(
                userService.findByEmail(email)
                );
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }
}

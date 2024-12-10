package org.ably.bankingsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.ably.bankingsecurity.domain.dto.InvoiceDTO;
import org.ably.bankingsecurity.domain.request.InvoiceRequest;
import org.ably.bankingsecurity.mapper.InvoiceMapper;
import org.ably.bankingsecurity.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/bill")
@Tag(name = "Invoice Controller", description = "Invoice management APIs")
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @Operation(summary = "Create new invoice")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDTO save(@RequestBody @Valid final InvoiceRequest invoiceRequest) {
        return invoiceMapper.toDTO(invoiceService.save(invoiceRequest));
    }

    @Operation(summary = "Get all invoices")
    @GetMapping("/")
    public List<InvoiceDTO> findAll() {
        return invoiceMapper.toDTOList(invoiceService.findAll());
    }

    @Operation(summary = "Delete invoice by ID")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable final Long id) {
        invoiceService.delete(id);
    }

    @Operation(summary = "Get invoice by ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Optional<InvoiceDTO> findById(@PathVariable final Long id) {
        return invoiceService.findById(id).map(invoiceMapper::toDTO);
    }
}
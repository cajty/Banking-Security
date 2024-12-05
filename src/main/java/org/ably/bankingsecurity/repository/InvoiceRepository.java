package org.ably.bankingsecurity.repository;

import org.ably.bankingsecurity.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}

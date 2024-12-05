package org.ably.bankingsecurity.repository;

import org.ably.bankingsecurity.domain.documents.TransactionDocument;
import org.ably.bankingsecurity.domain.enums.TransactionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TransactionSearchRepository extends ElasticsearchRepository<TransactionDocument, String> {
    List<TransactionDocument> findByAmountBetween(Double minAmount, Double maxAmount);
    List<TransactionDocument> findByType(TransactionType type);
    List<TransactionDocument> findBySenderUserIdOrReceiverUserId(String senderUserId, String receiverUserId);
}

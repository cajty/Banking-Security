package org.ably.bankingsecurity.domain.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ably.bankingsecurity.domain.entities.Transaction;
import org.ably.bankingsecurity.domain.enums.TransactionType;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDocument {
    @org.springframework.data.annotation.Id
    private String id;

    @Field(type = FieldType.Double)
    private Double amount;

    @Field(type = FieldType.Keyword)
    private TransactionType type;

    @Field(type = FieldType.Keyword)
    private String senderAccountId;

    @Field(type = FieldType.Keyword)
    private String senderUserId;

    @Field(type = FieldType.Keyword)
    private String receiverAccountId;

    @Field(type = FieldType.Keyword)
    private String receiverUserId;

    public static TransactionDocument fromEntity(Transaction transaction) {
        return TransactionDocument.builder()
                .id(transaction.getId().toString())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .senderAccountId(transaction.getAccountOfSender().getId().toString())
                .receiverAccountId(transaction.getAccountOfReceiver().getId().toString())
                .build();
    }
}


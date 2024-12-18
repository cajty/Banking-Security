//package org.ably.bankingsecurity.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import org.ably.bankingsecurity.domain.documents.TransactionDocument;
//import org.ably.bankingsecurity.domain.dto.TransactionDTO;
//import org.ably.bankingsecurity.domain.enums.TransactionType;
//import org.ably.bankingsecurity.domain.request.TransactionRequest;
//import org.ably.bankingsecurity.mapper.TransactionMapper;
//import org.ably.bankingsecurity.service.TransactionService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//import static org.springframework.http.HttpStatus.OK;
//
//@RestController
//@RequestMapping("/api/transaction")
//@Tag(name = "Transaction Controller", description = "Transaction management APIs")
//@AllArgsConstructor
//public class TransactionController {
//
//    private final TransactionService TransactionService;
//    private final TransactionMapper transactionMapper;
//
//    @Operation(summary = "Create new transaction")
//    @PostMapping("/add")
//    @ResponseStatus(HttpStatus.CREATED)
//    public TransactionDTO save(@RequestBody @Valid final TransactionRequest transactionRequest) {
//        return transactionMapper.toDTO(TransactionService.save(transactionRequest));
//    }
//
//    @Operation(summary = "Get all transactions")
//    @GetMapping("/")
//    public List<TransactionDTO> findAll() {
//        return TransactionService.findAll();
//    }
//
//    @Operation(summary = "Get all transactions of a user")
//    @GetMapping("/user/{userId}")
//    public List<TransactionDTO> findByUserId(@PathVariable final Long userId) {
//        return TransactionService.findByUserId(userId);
//    }
//
//    @Operation(summary = "Delete transaction by ID")
//    @DeleteMapping("/delete/{id}")
//    public void delete(@PathVariable final Long id) {
//        TransactionService.delete(id);
//    }
//
//    @Operation(summary = "Get transaction by ID")
//    @GetMapping("/{id}")
//    @ResponseStatus(OK)
//    public TransactionDTO findById(@PathVariable final Long id) {
//        return transactionMapper.toDTO(TransactionService.findById(id));
//    }
//    @Operation(summary = "Search transactions by amount range")
//    @GetMapping("/search/amount")
//    public ResponseEntity<List<TransactionDocument>> searchByAmountRange(
//            @RequestParam Double minAmount,
//            @RequestParam Double maxAmount) {
//        return ResponseEntity.ok(TransactionService.searchTransactionsByAmountRange(minAmount, maxAmount));
//    }
//@Operation(summary = "Search transactions by type")
//    @GetMapping("/search/type/{type}")
//    public ResponseEntity<List<TransactionDocument>> searchByType(@PathVariable TransactionType type) {
//        return ResponseEntity.ok(TransactionService.searchTransactionsByType(type));
//    }
//
//@Operation(summary = "Search transactions by user")
//    @GetMapping("/search/user/{userId}")
//    public ResponseEntity<List<TransactionDocument>> searchByUser(@PathVariable Long userId) {
//        return ResponseEntity.ok(TransactionService.searchTransactionsByUser(userId));
//    }
//}
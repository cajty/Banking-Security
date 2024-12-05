package org.ably.bankingsecurity.service;//package org.ably.bankinge.service;
//
//
//import org.ably.bankinge.domain.entities.Loan;
//import org.ably.bankinge.domain.entities.User;
//import org.ably.bankinge.domain.request.LoanRequest;
//import org.ably.bankinge.mapper.LoanMapper;
//import org.ably.bankinge.repository.LoanRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//@Service
//@AllArgsConstructor
//public class LoanService {
//    private final LoanRepository loanRepository;
//    private final LoanMapper loanMapper;
//    private final UserService userService;
//
//    public Loan save(LoanRequest loanRequest) {
//        User user = userService.findById(loanRequest.getUserId());
//
//        List<String> errors = validateUserEligibilityForLoan(user);
//        if (!errors.isEmpty()) {
//            throw new RuntimeException(String.join(", \n ", errors));
//        }
//
//
//        Loan loan = loanMapper.toEntity(loanRequest);
//        double interestRate = calculateInterestRate(loan.getPrincipal(), loan.getMonthlyPayment(), user.getCreditScore());
//        int termMonths = calcilatTermMonths(interestRate, loan.getPrincipal(), loan.getMonthlyPayment());
//
//        loan.setInterestRate(interestRate);
//        loan.setTermMonths(termMonths);
//
//
//
//
//        return loanRepository.save(loan);
//    }
//
//    public List<Loan> findAll() {
//        return loanRepository.findAll();
//    }
//
//    public Loan findById(Long id) {
//        return loanRepository.findById(id).orElseThrow(
//                () -> new RuntimeException("Loan not found with id " + id)
//        );
//    }
//
//    public List<Loan> findBYType(String type) {
//        return loanRepository.findByType(type);
//    }
//
//    public Loan validateLoan(Long id, boolean approved , Long userID ) {
//        Loan loan = findById(id);
//        User user = userService.findById(userID);
//
//        if (approved) {
//            loan.setApproved(true);
//            loan.setApprovedByUser(user);
//        } else {
//            loan.setApproved(false);
//            loan.setApprovedByUser(user);
//        }
//        loanRepository.save(loan);
//        return loan;
//    }
//
//    public void delete(Long id) {
//        loanRepository.deleteById(id);
//    }
//
//
//
//
//
//
//
//
//
//    private List<String> validateUserEligibilityForLoan(User user) {
//        List<String> errors = new ArrayList<>();
//        if (user.getAge() < 18) {
//            errors.add("User must be at least 18 years old");
//        }
//        if (user.getCreditScore() < 600) {
//            errors.add("Credit score must be at least 600");
//        }
//        if (user.getMonthlyIncome() <= 1000) {
//            errors.add("your monthly income  than 1000");
//        }
//        if (user.getCreatedAt().isAfter(LocalDate.now().minusMonths(6))) {
//
//            errors.add("User must have an account for at least 6 months");
//        }
//
//        return errors;
//    }
//
//
// private static int calcilatTermMonths(double interestRate, double amount, double mouthlyPayment) {
//    return (int) ((amount - mouthlyPayment) / interestRate);
//}
//
//
//
//private static double calculateInterestRate(double amount, double monthlyPayment, int creditScore) {
//    double interestRate = (amount - monthlyPayment) * creditScore / amount * 600;
//    if (interestRate < 1.02) {
//        interestRate = 1.02;
//    } else if (interestRate > 1.1) {
//        interestRate = 1.1;
//    }
//    return interestRate;
//}
//
//    }

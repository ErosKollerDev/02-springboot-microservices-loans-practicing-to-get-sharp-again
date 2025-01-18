package com.eroskoller.loans.repository;


import com.eroskoller.loans.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    Optional<LoanEntity> findByMobileNumber(String mobileNumber);
    Optional<LoanEntity> findByLoanNumber(String loanNumber);
}

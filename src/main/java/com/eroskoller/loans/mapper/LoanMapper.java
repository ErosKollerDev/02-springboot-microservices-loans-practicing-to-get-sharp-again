package com.eroskoller.loans.mapper;

import com.eroskoller.loans.dto.LoanDto;
import com.eroskoller.loans.entity.LoanEntity;

public class LoanMapper {


    public static LoanEntity mapToLoanEntity(LoanDto loanDto, LoanEntity loan) {
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutStandingAmount(loanDto.getOutstandingAmount());
        return loan;
    }

    public static LoanDto mapToLoanDto(LoanEntity loan) {

        return LoanDto.builder()
                .mobileNumber(loan.getMobileNumber())
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .totalLoan(loan.getTotalLoan())
                .amountPaid(loan.getAmountPaid())
                .outstandingAmount(loan.getOutStandingAmount())
                .build();
    }

}

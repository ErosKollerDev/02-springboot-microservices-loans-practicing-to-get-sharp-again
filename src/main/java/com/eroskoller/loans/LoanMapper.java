package com.eroskoller.loans;

import com.eroskoller.loans.dto.LoanDto;
import com.eroskoller.loans.entity.LoanEntity;

public class LoanMapper {


    public static LoanEntity mapToLoanEntity(LoanDto loanDto, LoanEntity loan) {
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutStandingAmount(loanDto.getOutStandingAmount());
        return loan;
    }

    public static LoanDto mapToLoanDto(LoanEntity loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutStandingAmount(loan.getOutStandingAmount());
        return loanDto;
    }

}

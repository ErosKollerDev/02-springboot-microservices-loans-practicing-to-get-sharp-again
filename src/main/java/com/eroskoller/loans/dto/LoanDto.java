package com.eroskoller.loans.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class LoanDto {

    @NotEmpty
    private String mobileNumber;
    @NotEmpty
    private String loanNumber;
    @NotEmpty
    private String loanType;
    @NotEmpty
    private Integer totalLoan;
    @NotEmpty
    private Integer amountPaid;
    @NotEmpty
    private Integer outStandingAmount;
}

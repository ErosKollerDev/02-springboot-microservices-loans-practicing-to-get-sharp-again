package com.eroskoller.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@Schema(name = "Loans",
        description = "Schema to hold Loan information"
)
public class LoanDto {

    @NotEmpty(message = "Mobile Number can not be a null or empty")
//    @Pattern(regexp="(^$|[0-9]{12})",message = "Mobile Number must be 12 digits")
    @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile number must be 11 digits")
    @Schema(
            description = "Mobile Number of Customer", example = "4365327698"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10,12})",message = "LoanNumber must be 12 digits")
    @Schema(
            description = "Loan Number of the customer", example = "548732457654"
    )
    private String loanNumber;

    @NotEmpty(message = "LoanType can not be a null or empty")
    @Schema(
            description = "Type of the loan", example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount", example = "100000"
    )
    @Min(value = 1, message = "Total loan amount should be greater than zero")
    @Max(value = 100000, message = "Total loan amount should be less than 100000")
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total loan amount paid", example = "1000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    private int outstandingAmount;
}

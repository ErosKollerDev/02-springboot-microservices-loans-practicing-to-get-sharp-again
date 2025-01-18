package com.eroskoller.loans.controller;


import com.eroskoller.loans.dto.ErrorResponseDto;
import com.eroskoller.loans.dto.LoanDto;
import com.eroskoller.loans.dto.ResponseDto;
import com.eroskoller.loans.entity.LoanEntity;
import com.eroskoller.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Loan Controller",
        description = "Loan Controller"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
@AllArgsConstructor
@Validated
public class LoanController {


    private final LoanService loanService;


    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside Db"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam(required = true)
//                                                  @Pattern(regexp="(^$|[0-9]{12})",message = "Mobile number must be 10 digits")
//            @Min(value = 10, message = "Mobile number must be 10 digits")
//            @Max(value = 12, message = "Mobile number must be 12 digits")
                                                      String mobileNumber) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(mobileNumber));
    }

    @Operation(
            summary = "Fetch Loan REST API",
            description = "REST API to fetch  loan inside Db"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> getLoan(@RequestParam(required = true) String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getLoan(mobileNumber));
    }


    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody @Valid LoanDto loanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.updateLoan(loanDto));
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam(required = true)
//                                                  @Pattern(regexp = "(^$[0-9]{11}$)",    message = "Mobile number must be 11 digits")
                                                      String mobileNumber) {
        this.loanService.deleteLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Loan deleted successfully"));
//        if (isDeleted) {
//        } else {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto("417", "Loan not deleted"));
//        }
    }


    @GetMapping("/loans")
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAllLoans());
    }
}

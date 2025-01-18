package com.eroskoller.loans.controller;


import com.eroskoller.loans.dto.ErrorResponseDto;
import com.eroskoller.loans.dto.LoanDto;
import com.eroskoller.loans.dto.ResponseDto;
import com.eroskoller.loans.entity.LoanEntity;
import com.eroskoller.loans.service.LoanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class LoanController {


    private final LoanService loanService;

    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> getLoan(@RequestParam(required = true) String loanNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getLoan(loanNumber));
    }


    @PostMapping("/create")
    public ResponseEntity<LoanDto> createLoan(@Valid @RequestBody LoanDto loanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(loanDto));
    }

    @PutMapping("/update")
    public ResponseEntity<LoanDto> updateLoan(@Valid @RequestBody LoanDto loanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.updateLoan(loanDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam(required = true) String loanNumber) {
        boolean isDeleted = this.loanService.deleteLoan(loanNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Loan deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto("417", "Loan not deleted"));
        }
    }


    @GetMapping("/loans")
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAllLoans());
    }
}

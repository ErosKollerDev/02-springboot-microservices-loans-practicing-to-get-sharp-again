package com.eroskoller.loans.service;


import com.eroskoller.loans.constants.LoansConstants;
import com.eroskoller.loans.dto.ResponseDto;
import com.eroskoller.loans.mapper.LoanMapper;
import com.eroskoller.loans.dto.LoanDto;
import com.eroskoller.loans.entity.LoanEntity;
import com.eroskoller.loans.exception.LoanAlreadyExistsException;
import com.eroskoller.loans.exception.LoanDoesntExistsException;
import com.eroskoller.loans.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanService {


    private final LoanRepository loanRepository;


    public LoanDto getLoan(String mobileNumber) {
        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByMobileNumber(mobileNumber);
        if (loanRepositoryByLoanNumber.isPresent()) {
            return LoanMapper.mapToLoanDto(loanRepositoryByLoanNumber.get());
        } else {
            throw new LoanDoesntExistsException("Loan doesnt exists");
        }
    }


    public List<LoanDto> getAllLoans() {
        List<LoanEntity> allEntities = loanRepository.findAll();
//        ArrayList<LoanDto> loanDtos = new ArrayList<>();
//
//        for (LoanEntity entity : allEntities) {
//
//
//        }
//        return loanDtos;
        return allEntities.stream()
                .map(LoanMapper::mapToLoanDto)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public ResponseDto createLoan(String mobileNumber) {
        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByMobileNumber(mobileNumber);


        if (loanRepositoryByLoanNumber.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists");
        }

        LoanDto loanDto = LoanDto.builder()
                .loanType(LoansConstants.HOME_LOAN)
                .totalLoan(LoansConstants.NEW_LOAN_LIMIT)
                .amountPaid(0)
                .outstandingAmount(LoansConstants.NEW_LOAN_LIMIT)
                .mobileNumber(mobileNumber)
                .loanNumber(Math.round(Math.random() * 999999999999L) + "")
                .build();

        LoanEntity loanEntity = LoanMapper.mapToLoanEntity(loanDto, new LoanEntity());
//        loanEntity.setLoanNumber(Math.round(Math.random()*999999999999L)+"");
        this.loanRepository.save(loanEntity);
        return new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201);

    }


    public ResponseDto updateLoan(LoanDto loanDto) {
        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByLoanNumber(loanDto.getLoanNumber());

        if (loanRepositoryByLoanNumber.isPresent()) {
            LoanEntity loanEntity = LoanMapper.mapToLoanEntity(loanDto, loanRepositoryByLoanNumber.get());
            this.loanRepository.save(loanEntity);
            return new ResponseDto("200", "Loan updated successfully");
        } else {
            throw new LoanDoesntExistsException("Loan doesnt exists");

        }

    }

    public boolean deleteLoan(String mobileNumber) {

        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByMobileNumber(mobileNumber);
        if (loanRepositoryByLoanNumber.isPresent()) {
            this.loanRepository.delete(loanRepositoryByLoanNumber.get());
            return true;
        } else {
            throw new LoanDoesntExistsException("Loan doesnt exists");
        }

    }
}

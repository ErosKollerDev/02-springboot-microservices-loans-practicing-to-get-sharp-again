package com.eroskoller.loans.service;


import com.eroskoller.loans.LoanMapper;
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


    public LoanDto getLoan(String loanNumber) {
        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByLoanNumber(loanNumber);
        if (loanRepositoryByLoanNumber.isPresent()) {
            return LoanMapper.mapToLoanDto(loanRepositoryByLoanNumber.get());
        } else {
            throw new LoanDoesntExistsException("Loan doesnt exists");
        }
    }


    public List<LoanDto> getAllLoans() {
        List<LoanEntity> allEntities = loanRepository.findAll();
        ArrayList<LoanDto> loanDtos = new ArrayList<>();

        for (LoanEntity entity : allEntities) {
            LoanDto loanDto = new LoanDto();
            loanDto.setMobileNumber(entity.getMobileNumber());
            loanDto.setLoanNumber(entity.getLoanNumber());
            loanDto.setLoanType(entity.getLoanType());
            loanDto.setTotalLoan(entity.getTotalLoan());
            loanDto.setAmountPaid(entity.getAmountPaid());
            loanDto.setOutStandingAmount(entity.getOutStandingAmount());
            loanDtos.add(loanDto);
        }
        return loanDtos;
    }

    public LoanDto createLoan(@Valid LoanDto loanDto) {
        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByLoanNumber(loanDto.getLoanNumber());


        if (loanRepositoryByLoanNumber.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists");
        }
        LoanEntity loanEntity = LoanMapper.mapToLoanEntity(loanDto, new LoanEntity());
        this.loanRepository.save(loanEntity);
        return LoanMapper.mapToLoanDto(loanEntity);

    }


    public LoanDto updateLoan(@Valid LoanDto loanDto) {
        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByLoanNumber(loanDto.getLoanNumber());

        if (loanRepositoryByLoanNumber.isPresent()) {
            LoanEntity loanEntity = LoanMapper.mapToLoanEntity(loanDto, loanRepositoryByLoanNumber.get());
            LoanEntity updated = this.loanRepository.save(loanEntity);
            return LoanMapper.mapToLoanDto(updated);
        } else {
            throw new LoanAlreadyExistsException("Loan doesnt exists");

        }

    }

    public boolean deleteLoan(String loanNumber) {

        Optional<LoanEntity> loanRepositoryByLoanNumber = this.loanRepository.findByLoanNumber(loanNumber);
        if (loanRepositoryByLoanNumber.isPresent()) {
            this.loanRepository.delete(loanRepositoryByLoanNumber.get());
            return true;
        } else {
            return false;
        }

    }
}

package com.eroskoller.loans.controller;


import com.eroskoller.loans.dto.ErrorResponseDto;
import com.eroskoller.loans.dto.LoanDto;
import com.eroskoller.loans.dto.LoansContactInfoDto;
import com.eroskoller.loans.dto.ResponseDto;
import com.eroskoller.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
//@AllArgsConstructor
@Validated
public class LoanController {


    @Value("${build.version}")
    private String buildVersion;
    private final LoanService loanService;
    private final LoansContactInfoDto loansContactInfoDto;
    private Environment environment;

    public LoanController(LoanService loanService, LoansContactInfoDto loansContactInfoDto, Environment environment) {
        this.loanService = loanService;
        this.loansContactInfoDto = loansContactInfoDto;
        this.environment = environment;
    }

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
    public ResponseEntity<LoanDto> getLoan(@Valid
                                           @RequestParam(required = true)
                                           @Pattern(regexp = "(^$|[0-9]{9,11})", message = "Mobile number must be between 9 and 11 digits")
                                           String mobileNumber) {
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
                                                  @Pattern(regexp = "(^$|[0-9]{9,11})", message = "Mobile number must be between 9 and 11 digits")
                                                  String mobileNumber) {
        this.loanService.deleteLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Loan deleted successfully"));
//        if (isDeleted) {
//        } else {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto("417", "Loan not deleted"));
//        }
    }

    @Operation(
            summary = "Get All Loans REST API",
            description = "REST API to get all loan details based on a mobile number"
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
    })
    @GetMapping("/loans")
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAllLoans());
    }


    @Operation(
            summary = "Get Build Version REST API",
            description = "REST API to get build version"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.ok(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }

}

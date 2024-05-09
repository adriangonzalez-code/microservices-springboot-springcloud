package com.driagon.accountsservice.app.controllers;

import com.driagon.accountsservice.app.constants.AccountsConstants;
import com.driagon.accountsservice.app.dto.AccountsContactInfoDto;
import com.driagon.accountsservice.app.dto.CustomerDto;
import com.driagon.accountsservice.app.dto.ErrorResponseDto;
import com.driagon.accountsservice.app.dto.ResponseDto;
import com.driagon.accountsservice.app.services.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@Tag(name = "CRUD REST APIs for Accounts in EazyBank", description = "CRUD REST AOIs in EazyBank to CREATE, FETCH, UPDATE and DELETE account details")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@Slf4j
public class AccountsController {

    @Value("${build.version}")
    private String buildVersion;

    private final IAccountService accountService;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create Account REST API", description = "REST API to create new Customer & Account inside EazyBank")
    @ApiResponse(responseCode = "201", description = "HTTP Status Created")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto request) {
        this.accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Account Details REST API", description = "REST API to fetch Customer & Account details based on a mobile number")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must have 10 digits") String mobileNumber) {
        CustomerDto customerDto = this.accountService.fetchAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(summary = "Update Account Details REST API", description = "REST API to update Customer & Account details based on an account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto request) {
        boolean isUpdated = this.accountService.updateAccount(request);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(summary = "Delete Account & Customer Details REST API", description = "REST API to delete Customer & Account details based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must have 10 digits") String mobileNumber) {
        boolean isDeleted = this.accountService.deleteAccount(mobileNumber);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    @Operation(summary = "Get Build information", description = "Get Build information that is deployed into accounts microservice")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<String> getBuildInfo() throws TimeoutException {
        log.info("getBuildInfo() method Invoked");
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    @Operation(summary = "Get Java version information", description = "Get Java versions that is installed into accounts microservice")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    @Operation(summary = "Get Contact information", description = "Contact info details that can be reached out in case of any issues")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        log.info("Invoked Accounts contact-info API");
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        log.info("getBuildInfoFallback() method Invoked");
        return ResponseEntity.status(HttpStatus.OK).body("0.9");
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.OK).body("Java 17");
    }
}
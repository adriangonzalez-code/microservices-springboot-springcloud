package com.driagon.accountsservice.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Accounts, Cards and Loans information"
)
public class CustomerDetailsDto implements Serializable {

    private static final long serialVersionUID = 6627125772390691559L;

    @Schema(description = "Name of the customer", example = "Eazy Bytes")
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(description = "Email address of the customer", example = "tutor@eazybytes.com")
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "9876543210")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must have 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDto accountsDto;

    @Schema(description = "Cards details of the customer")
    private CardsDto cardsDto;

    @Schema(description = "Loans details of the customer")
    private LoansDto loansDto;
}
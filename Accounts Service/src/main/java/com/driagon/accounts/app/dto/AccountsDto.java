package com.driagon.accounts.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(description = "Account number of Eazy Bank account", example = "65413514")
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "^|[0-9]{10}", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account type of Eazy Bank account", example = "Savings")
    @NotEmpty(message = "Account type can not be a null or empty")
    private String accountType;

    @Schema(description = "Eazy Bank branch address", example = "123 New York")
    @NotEmpty(message = "Branch address can not be null or empty")
    private String branchAddress;
}
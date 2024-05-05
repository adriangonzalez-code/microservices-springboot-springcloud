package com.driagon.accountsservice.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Schema(
        name = "Card",
        description = "Schema to hold Card information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CardsDto implements Serializable {

    private static final long serialVersionUID = 4593937553957221929L;

    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile Number of Customer", example = "9876543210"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card Number can not be a null or empty")
    @Pattern(regexp = "^$|[0-9]{12}", message = "Card Number must be 12 digits")
    @Schema(
            description = "Card Number of the Customer", example = "100564577885"
    )
    private String cardNumber;

    @NotEmpty(message = "Card Type can not be a null or empty")
    @Schema(
            description = "Type of card", example = "Credit Card"
    )
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(
            description = "Total amount limit available against a card", example = "100000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal ot greater than zero")
    @Schema(
            description = "Total available amount against a card", example = "9000"
    )
    private int availableAmount;
}
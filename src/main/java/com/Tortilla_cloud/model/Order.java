package com.Tortilla_cloud.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
public class Order {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zip;

    @NotBlank(message = "Card number is required")
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @NotBlank(message = "Expiry is required")
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @NotBlank(message = "CVV is required")
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;
}

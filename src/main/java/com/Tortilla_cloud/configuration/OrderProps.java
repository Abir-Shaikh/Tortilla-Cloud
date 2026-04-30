package com.Tortilla_cloud.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "tortilla.orders")
public class OrderProps {

    @Min(value = 5 , message = "Page size must be between 5 and 25")
    @Max(value = 25 , message = "Page size must be between 5 and 25")
    private int pageSize = 20;
}

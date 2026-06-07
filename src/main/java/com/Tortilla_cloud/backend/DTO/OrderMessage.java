package com.Tortilla_cloud.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {

    @NotNull
    private Long orderId;

    @NotBlank(message = "customer name is required")
    @Size(min = 2 , message = "name must contain 2 letters")
    private String customerName;

    @NotBlank
    @Email
    private String customerEmail;

    @NotBlank
    private String deliveryCity;

    @NotBlank
    private String deliveryState;
    private String message;
    private long timestamp;

    public static OrderMessage from(Long orderId , String customerName , String city , String state){
        return new OrderMessage(
                orderId,
                customerName,
                customerName.toLowerCase().replace(" " , ".") + "@tortillacloud.com",
                city,
                state,
                "New Order placed",
                System.currentTimeMillis()
        );
    }
}

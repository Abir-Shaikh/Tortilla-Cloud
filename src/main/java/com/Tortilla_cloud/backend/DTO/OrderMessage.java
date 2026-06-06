package com.Tortilla_cloud.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {

    private Long orderId;
    private String customerName;
    private String customerEmail;
    private String deliveryCity;
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

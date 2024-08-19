package com.soft.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CartDto {
    private Integer cartId;
    private Integer quantity;

    private LocalDateTime created;
    private LocalDateTime updated;
//    private Integer statu;
}

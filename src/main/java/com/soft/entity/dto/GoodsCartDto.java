package com.soft.entity.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsCartDto {
    private Integer goodsId;
    private String goodsName;
    private String goodsExplain;
    private String goodsImg;
    private BigDecimal goodsPrice;
    private Long businessId;
//    private Integer statu;


    private CartDto cart;

}

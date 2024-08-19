package com.soft.entity.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class BusinessCartDto {
    private Long businessId;
    private String businessName;
    private String businessAddress;
    private String businessExplain;
    private String businessImg;
    private BigDecimal starPrice;
    private BigDecimal deliveryPrice;
//    private Integer statu;

    private List<GoodsCartDto> goods;
}

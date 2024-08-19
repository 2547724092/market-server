package com.soft.entity.dto;


import lombok.Data;

import java.util.List;
@Data
public class AccountCartDto {
    private String accountId;
    private String accountName;
    private Integer accountSex;
    private String accountImg;
//    private Integer statu;
    private List<BusinessCartDto> businessList;
}

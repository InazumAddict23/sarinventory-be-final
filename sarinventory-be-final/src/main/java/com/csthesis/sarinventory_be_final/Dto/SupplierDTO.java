package com.csthesis.sarinventory_be_final.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SupplierDTO {
    private Long id;
    private String name;
    private String phoneNumber;
}

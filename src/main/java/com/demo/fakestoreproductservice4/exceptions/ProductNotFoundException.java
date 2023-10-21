package com.demo.fakestoreproductservice4.exceptions;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends Exception {
    // Fields
    private Integer errCode;
    private String errMsg;

    // CTOR
    public ProductNotFoundException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}

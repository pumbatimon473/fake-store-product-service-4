package com.demo.fakestoreproductservice4.exceptions;

import lombok.Getter;

@Getter
public class ClientErrorException extends Exception {
    // Fields
    private Integer errCode;
    private String errMsg;

    public ClientErrorException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "ClientErrorException{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}

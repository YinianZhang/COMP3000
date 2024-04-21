package com.example.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "Successful"),

    PARAM_ERROR("400", "Parameter Exception"),
    TOKEN_INVALID_ERROR("401", "Invalid Token"),
    TOKEN_CHECK_ERROR("401", "Token Verification Failed, Please Login Again"),
    PARAM_LOST_ERROR("4001", "Missing Parameter"),

    SYSTEM_ERROR("500", "System Exception"),
    USER_EXIST_ERROR("5001", "Username Already Exists"),
    USER_NOT_LOGIN("5002", "User Not Logged In"),
    USER_ACCOUNT_ERROR("5003", "Incorrect Username or Password"),
    USER_NOT_EXIST_ERROR("5004", "User Does Not Exist"),
    PARAM_PASSWORD_ERROR("5005", "Incorrect Original Password"),
    ACCOUNT_LOW_ERROR("5006", "Wallet balance is insufficient. Please recharge at the personal center!"),
    ROOM_NUM_ERROR("5007", "Number Of Rooms Insufficient "),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

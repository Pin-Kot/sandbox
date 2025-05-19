package com.minsk.frontendpracticeservice.validation;

public class Utils {

    public static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{20}$";
    public static final String BIC_OR_KPP_REGEX = "^[0-9]{9}$";
    public static final String INN_REGEX = "^[0-9]{12}$";
    public static final String KBK_REGEX = "^[0-9]{20}$";
    public static final String CARD_REGEX = "^[0-9]{16}$";
    public static final String UUID_REGEX = "^[{]{0,1}[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}[}]{0,1}$";

}

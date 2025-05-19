package com.minsk.frontendpracticeservice.domain.request;

import com.minsk.frontendpracticeservice.validation.Utils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequisitesCreateRequestDto {

    @NotBlank
    @Pattern(regexp = Utils.ACCOUNT_NUMBER_REGEX, message = "Расчетный счет должен состоять из 20 цифр")
    private String accountNumber;

    @NotBlank
    @Pattern(regexp = Utils.BIC_OR_KPP_REGEX, message = "БИК должен состоять из 9 цифр")
    private String bic;

    @NotBlank
    @Pattern(regexp = Utils.ACCOUNT_NUMBER_REGEX, message = "Корреспондентский счет должен состоять из 20 цифр")
    private String correspondentAccount;

    @NotBlank
    @Pattern(regexp = Utils.INN_REGEX, message = "ИНН должен состоять из 12 цифр")
    private String inn;

    @NotBlank
    @Pattern(regexp = Utils.BIC_OR_KPP_REGEX, message = "КПП должен состоять из 9 цифр")
    private String kpp;

    @NotBlank
    @Pattern(regexp = Utils.KBK_REGEX, message = "КБК должен состоять из 20 цифр")
    private String kbk;

}

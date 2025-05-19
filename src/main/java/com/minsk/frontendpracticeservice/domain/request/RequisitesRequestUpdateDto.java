package com.minsk.frontendpracticeservice.domain.request;

import com.minsk.frontendpracticeservice.validation.Utils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequisitesRequestUpdateDto {

        @NotNull
        private UUID requisitesId;

        @NotBlank
        @Pattern(regexp = Utils.BIC_OR_KPP_REGEX, message = "БИК должен состоять из 9 цифр")
        private String bic;

        @NotBlank
        @Pattern(regexp = Utils.ACCOUNT_NUMBER_REGEX, message = "Корреспондентский счет должен состоять из 20 цифр")
        private String correspondentAccount;

        @NotBlank
        @Pattern(regexp = Utils.BIC_OR_KPP_REGEX, message = "КПП должен состоять из 9 цифр")
        private String kpp;

        @NotBlank
        @Pattern(regexp = Utils.KBK_REGEX, message = "КБК должен состоять из 20 цифр")
        private String kbk;

}

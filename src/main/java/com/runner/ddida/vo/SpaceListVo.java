package com.runner.ddida.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SpaceListVo {
    private String rsrcClsCd;
    private String rsrcClscNm;
    private String upRsrcClsCd;
    private String rsrcClsDfin;
    private String rsrcTypeCd1;
    private String rsrcTypeCd2;
    private String rsrcTypeCd3;
    private String useYn;
}

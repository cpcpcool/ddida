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
public class SpaceVo {
    private String rsrcNo;
    private String rsrcNm;
    private String zip;
    private String addr;
    private String daddr;
    private String lot;
    private String lat;
    private String instUrlAddr;
    private String imgFileUrlAddr;
}

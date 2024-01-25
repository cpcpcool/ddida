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
public class SpaceDetailVo {
	private String rsrcNo;
	private String rsrcNm;
	private String rsrcClsCd;
	private String rsrcClsNm;
	private String rsrcInstCd;
	private String rsrcInstNm;
    private String linkRsrcYn;
    private String usePsblYn;
    private String freeYn;
    private String rsrcIntr;
    private String usePrpse;
    private String atpn;
    private String zip;
    private String addr;
    private String daddr;
    private String lot;
    private String lat;
    private String lcInf;
    private String area;
    private String inqTag;
    private String lossYn;
    private int amt1;
    private int amt2;
    private String updYmd;
    private String bnrImgFileUrlAddr;
    private String bnrImgFileUrl;
    private String delYn;
    private String rsrcAprvYn;
    private String rsrcAprvYmd;
    private String instUrlAddr;
    private String sggCd;
    private String dtlUrlAddr;
    private String gdsAtrbCn;
    private String delYmd;
    private String rsvtNdlsYn;
    private String lcInsttCd;
    private String lcInsttNm;
}

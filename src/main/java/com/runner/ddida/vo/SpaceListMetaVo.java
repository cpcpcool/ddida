package com.runner.ddida.vo;

import java.util.List;

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
public class SpaceListMetaVo {
    private String resultCode;
    private String resultMsg;
    private List<SpaceListVo> data;
    
}

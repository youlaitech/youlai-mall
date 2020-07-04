package com.youlai.sc.oss.core;


import com.youlai.sc.oss.strategy.OssStrategy;
import lombok.Getter;
import lombok.Setter;

public class OssService {

    @Getter
    @Setter
    private OssStrategy ossStrategy;

}

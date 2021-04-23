package com.youlai.admin.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.youlai.admin.pojo.vo.UserVO;
import com.youlai.common.result.Result;

/**
 * @author haoxr
 * @description TODO
 * @createTime 2021/4/23 23:30
 */
public class UserBlockHandler {
    public static Result<UserVO> handleGetCurrentUserBlock(BlockException blockException) {
        return Result.success(new UserVO());
    }
}

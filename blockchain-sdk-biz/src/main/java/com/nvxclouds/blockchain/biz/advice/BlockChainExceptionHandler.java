package com.nvxclouds.blockchain.biz.advice;

import com.nvxclouds.blockchain.biz.enums.BlockchainExceptionEnum;
import com.nvxclouds.blockchain.biz.exception.BlockchainException;
import com.nvxclouds.common.base.pojo.BaseResult;
import com.nvxclouds.common.core.advice.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/5/21 13:23
 * @Description:
 */
@RestController
public class BlockChainExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler({BlockchainException.class})
    public BaseResult<Object> handleFeignException(BlockchainException exception) {
        exception.printStackTrace();
        BlockchainExceptionEnum blockchainException = exception.getBlockchainExceptionEnum();
        return BaseResult.builder()
                .msg(blockchainException.getMsg())
                .code(blockchainException.getCode())
                .build();
    }
}

package com.nvxclouds.blockchain.biz.exception;

import com.nvxclouds.blockchain.biz.enums.BlockchainExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/5/25 11:27
 * @Description:
 */
@AllArgsConstructor
@Getter
public class BlockchainException extends RuntimeException {
    private final BlockchainExceptionEnum blockchainExceptionEnum;
}

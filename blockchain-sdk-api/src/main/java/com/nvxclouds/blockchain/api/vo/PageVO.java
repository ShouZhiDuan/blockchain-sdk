package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/28 17:52  zhengxing.hu
 * @version 2020/6/28 17:52  1.0.0
 * @file PageVO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class PageVO {
    private Long totalCount;
    private Long totalPage;
    private Long pageSize;
    private Long offsets;
}

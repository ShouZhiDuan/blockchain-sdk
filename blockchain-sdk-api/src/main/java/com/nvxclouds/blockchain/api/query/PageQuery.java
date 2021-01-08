package com.nvxclouds.blockchain.api.query;

import com.nvxclouds.common.base.pojo.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 2020/6/29 13:50  zhengxing.hu
 * @version 2020/6/29 13:50  1.0.0
 * @file PageQuery
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Getter
@Setter
public class PageQuery  extends Page {
    private String startTime;
    private String endTime;
}

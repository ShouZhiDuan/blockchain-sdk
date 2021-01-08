package com.nvxclouds.blockchain.biz.service.impl;

import com.google.common.collect.Lists;
import com.nvxclouds.blockchain.api.query.DataSetApprovalModificationQuery;
import com.nvxclouds.blockchain.api.query.DataSetApprovalQuery;
import com.nvxclouds.blockchain.api.query.StudyProjectApprovalQuery;
import com.nvxclouds.blockchain.api.vo.DataSetApprovalModificationVO;
import com.nvxclouds.blockchain.api.vo.DataSetApprovalVO;
import com.nvxclouds.blockchain.api.vo.StudyProjectApprovalVO;
import com.nvxclouds.blockchain.biz.config.Config;
import com.nvxclouds.blockchain.biz.constant.TimeConstant;
import com.nvxclouds.blockchain.biz.mapper.InvokeInfoMapper;
import com.nvxclouds.blockchain.biz.query.BlockchainTransactionApprovalQuery;
import com.nvxclouds.blockchain.biz.service.BlockchainService;
import com.nvxclouds.blockchain.biz.service.MSService;
import com.nvxclouds.blockchain.biz.vo.BlockchainTransactionApprovalVO;
import com.nvxclouds.common.base.pojo.Pagination;
import com.nvxclouds.common.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author 2020/7/1 10:26  zhengxing.hu
 * @version 2020/7/1 10:26  1.0.0
 * @file BlockchainServiceImpl
 * @brief 区块链相关业务处理层
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Service
public class BlockchainServiceImpl extends BaseService implements BlockchainService {

    @Autowired
    private InvokeInfoMapper invokeInfoMapper;


    @Autowired
    private MSService msService;


    @Override
    public Pagination<BlockchainTransactionApprovalVO> queryBlockchainTransactionApproval(BlockchainTransactionApprovalQuery query) {
        List<BlockchainTransactionApprovalVO> blockchainTransactionApprovals = new ArrayList<>();
        queryDataSetApproval(blockchainTransactionApprovals);
        queryStudyProjectApproval(blockchainTransactionApprovals);
        blockchainTransactionApprovals.sort(Comparator.comparing(BlockchainTransactionApprovalVO::getApprovalTime).reversed());
        List<List<BlockchainTransactionApprovalVO>> partition = Lists.partition(blockchainTransactionApprovals, query.getPerPage());
        Pagination<BlockchainTransactionApprovalVO> pagination = new Pagination<>();
        if (query.getPage() > partition.size()) {
            return pagination;
        }
        pagination.setList(partition.get(query.getPage()-1));
        pagination.setPages(partition.size());
        pagination.setPage(query.getPage());
        pagination.setPerPage(query.getPerPage());
        pagination.setTotal(blockchainTransactionApprovals.size());
        return pagination;
    }

    private void queryStudyProjectApproval(List<BlockchainTransactionApprovalVO> blockchainTransactionApprovals) {
        StudyProjectApprovalQuery query = new StudyProjectApprovalQuery();
        query.setTxTime(getTxTime());
        StudyProjectApprovalVO studyProjectApprovalVO = msService.queryStudyProjectApproval(query);
        Optional.ofNullable(studyProjectApprovalVO).ifPresent(vo -> {
            if (!CollectionUtils.isEmpty(vo.getList())) {
                vo.getList().forEach(info ->
                        blockchainTransactionApprovals.add(BlockchainTransactionApprovalVO.builder()
                                .operation(info.getOperation())
                                .hash(info.getMessageHash())
                                .approvalTime(info.getTxTime())
                                .peerNode(Config.REGULATOR_PEER_1)
                                .transactionId(info.getTransactionID())
                                .approvalStatus(info.getStatus())
                                .build())
                );
            }
        });
    }


    private void queryDataSetApprovalModification(List<BlockchainTransactionApprovalVO> blockchainTransactionApprovals) {
        DataSetApprovalModificationQuery query = new DataSetApprovalModificationQuery();
        query.setTxTime(getTxTime());
        DataSetApprovalModificationVO dataSetApprovalModificationVO = msService.queryDataSetApprovalModification(query);
        List<DataSetApprovalModificationVO.DataSetApprovalModificationInfoVO> dataSetApprovalModifications = dataSetApprovalModificationVO.getList();
        if (CollectionUtils.isEmpty(dataSetApprovalModifications)) {
            return;
        }
        dataSetApprovalModifications.forEach(info ->
                blockchainTransactionApprovals.add(BlockchainTransactionApprovalVO.builder()
                        .operation(info.getOperation())
                        .hash(info.getMessageHash())
                        .approvalTime(info.getTxTime())
                        .peerNode(Config.REGULATOR_PEER_1)
                        .transactionId(info.getTransactionID())
                        .approvalStatus(info.getStatus())
                        .build())
        );

    }

    private void queryDataSetApproval(List<BlockchainTransactionApprovalVO> blockchainTransactionApprovals) {
        DataSetApprovalQuery dataSetApproval = new DataSetApprovalQuery();
        dataSetApproval.setTxTime(getTxTime());
        DataSetApprovalVO dataSetApprovalVO = msService.queryDataSetApproval(dataSetApproval);
        List<DataSetApprovalVO.DataSetApprovalInfoVO> dataSetApprovals = dataSetApprovalVO.getList();
        if (CollectionUtils.isEmpty(dataSetApprovals)) {
            return;
        }
        dataSetApprovals.forEach(info ->
                blockchainTransactionApprovals.add(BlockchainTransactionApprovalVO.builder()
                        .operation(info.getOperation())
                        .hash(info.getMessageHash())
                        .approvalTime(info.getTxTime())
                        .peerNode(Config.REGULATOR_PEER_1)
                        .transactionId(info.getTransactionID())
                        .approvalStatus(info.getStatus())
                        .build())
        );
    }

    private String getTxTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TimeConstant.yyyyMMdd));
    }
}

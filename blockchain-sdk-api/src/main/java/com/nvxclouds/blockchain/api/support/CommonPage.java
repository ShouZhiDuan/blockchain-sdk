package com.nvxclouds.blockchain.api.support;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据封装类
 * Created by dsz on 2020/05/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPage<T> {
    private Integer page;//当前是第几页
    private Integer perPage;//每页显示几条
    private Integer pages;//一共多少页
    private Long total;//总共有多少条结果数据
    private List<T> list;//当前页数据集合
    private Boolean end;//是否是最后一页 true表示最后一页 false表示不是最后一页

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setPages(pageInfo.getPages());
        result.setPage(pageInfo.getPageNum());
        result.setPerPage(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        result.setEnd(pageInfo.getPageNum() < pageInfo.getPages() ? false : true);
        return result;
    }

    /**
     * 物理分页
     *
     * @param originList 分页前数据
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 分页后结果
     */
    public static  <T> CommonPage<T> getPageByAllList(List<T> originList, Integer pageNum, Integer pageSize) {
        // 如果页码为空或者每页数量为空
        pageNum  = (pageNum == null  || pageNum  == 0) ? 1 : pageNum; //每页显示多少条
        pageSize = (pageSize == null || pageSize == 0) ? 10 : pageSize;//第几页 默认第一页

        // 分页后的结果
        List<T> resultList = new ArrayList<>();

        // 如果需要进行分页
        if (pageNum > 0 && pageSize > 0) {
            // 获取起点
            int pageStart = (pageNum - 1) * pageSize;
            // 获取终点
            int pageStop = pageStart + pageSize;
            // 开始遍历
            while (pageStart < pageStop) {
                // 考虑到最后一页可能不够pageSize
                if (pageStart == originList.size()) {
                    break;
                }
                resultList.add(originList.get(pageStart++));
            }
        }
        // 如果不进行分页
        else {
            // 显示所有数据
            resultList = originList;
        }
        CommonPage<T> result = new CommonPage<T>();
        result.setPage(pageNum);//设置第几页
        result.setPerPage(pageSize);//设置每页显示几条
        result.setPages((originList.size()%pageSize == 0) ? (originList.size()/pageSize) : (originList.size()/pageSize) + 1);
//        result.setPage(pageNum);
//        result.setPerPage(pageSize);
        result.setTotal(Long.valueOf(originList.size()));
        result.setList(resultList);
        result.setEnd(pageNum < ((originList.size()%pageSize == 0) ? (originList.size()/pageSize) : (originList.size()/pageSize) + 1) ? false : true);
        return result;
    }

}

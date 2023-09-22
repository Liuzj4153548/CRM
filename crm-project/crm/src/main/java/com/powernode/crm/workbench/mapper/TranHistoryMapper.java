package com.powernode.crm.workbench.mapper;

import com.powernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Apr 23 00:03:49 GMT+08:00 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Apr 23 00:03:49 GMT+08:00 2023
     */
    int insert(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Apr 23 00:03:49 GMT+08:00 2023
     */
    int insertSelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Apr 23 00:03:49 GMT+08:00 2023
     */
    TranHistory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Apr 23 00:03:49 GMT+08:00 2023
     */
    int updateByPrimaryKeySelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Sun Apr 23 00:03:49 GMT+08:00 2023
     */
    int updateByPrimaryKey(TranHistory record);

    /**
     * 保存创建的交易历史
     * @param tranHistory
     * @return
     */
    int insertTranHistory(TranHistory tranHistory);

    /**
     * 根据tranID查询该交易下所有历史明细信息
     * @param tranId
     * @return
     */
    List<TranHistory> selectTranHistoryForDetailByTranId(String tranId);
}
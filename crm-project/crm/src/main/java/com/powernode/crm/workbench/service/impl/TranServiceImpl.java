package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.workbench.domain.Customer;
import com.powernode.crm.workbench.domain.FunnelVO;
import com.powernode.crm.workbench.domain.Tran;
import com.powernode.crm.workbench.domain.TranHistory;
import com.powernode.crm.workbench.mapper.CustomerMapper;
import com.powernode.crm.workbench.mapper.TranHistoryMapper;
import com.powernode.crm.workbench.mapper.TranMapper;
import com.powernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @title:TranServiceImpl Author liu
 * @Date:2023/4/21 17:52
 * @Version 1.0
 */
@Service("tranServiceImpl")
public class TranServiceImpl implements TranService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    public void saveCreateTran(Map<String, Object> map) {
        String customerName = (String)map.get("customerName");
        User user =(User) map.get(Constants.SESSION_USER);
        Customer customer = customerMapper.selectCustomerByName(customerName);
        //不存在就新建
        if (customer == null) {
            customer = new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setId(UUIDUtils.getUUID());
            customer.setCreateTime(DateUtils.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());

            customerMapper.insertCustomer(customer);
        }

        //保存创建交易
        Tran tran = new Tran();
        tran.setStage((String) map.get("stage"));
        tran.setOwner((String) map.get("owner"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        tran.setName((String) map.get("name"));
        tran.setMoney((String) map.get("money"));
        tran.setId(UUIDUtils.getUUID());
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setContactsId((String) map.get("contactsId"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setDescription((String) map.get("description"));
        tran.setSource((String) map.get("source"));
        tran.setType((String) map.get("type"));

        //调用交易的mapper保存
        tranMapper.insertTran(tran);

        //保存交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());

        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    public List<FunnelVO> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}

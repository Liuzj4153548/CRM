package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.commons.constants.Constants;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.workbench.domain.*;
import com.powernode.crm.workbench.mapper.*;
import com.powernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @title:ClueServiceImpl Author liu
 * @Date:2023/4/14 17:59
 * @Version 1.0
 */
@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    public void saveConvertClue(Map<String, Object> map) {
        String clueId = (String)map.get("clueId");
        User user = (User)map.get(Constants.SESSION_USER);
        String isCreateTran =(String) map.get("isCreateTran");
        //根据id查询线索的信息
        Clue clue = clueMapper.selectClueById(clueId);
        //把该线索中有关公司信息转换到客户表中
        Customer c = new Customer();
        c.setAddress(clue.getAddress());
        c.setContactSummary(clue.getContactSummary());
        c.setCreateBy(user.getId());
        c.setCreateTime(DateUtils.formatDateTime(new Date()));
        c.setDescription(clue.getDescription());
        c.setId(UUIDUtils.getUUID());
        c.setName(clue.getCompany());
        c.setNextContactTime(clue.getNextContactTime());
        c.setOwner(user.getId());
        c.setPhone(clue.getPhone());
        c.setWebsite(clue.getWebsite());

        //注入客户的mapper，调用新建客户方法插入数据
        customerMapper.insertCustomer(c);
        /*
        //把线索中有关于个人的信息转换到联系人表中
        Contacts con = new Contacts();
        con.setAddress(clue.getAddress());
        con.setAppellation(clue.getAppellation());
        con.setContactSummary(clue.getContactSummary());
        con.setCreateBy(user.getId());
        con.setCreateTime(DateUtils.formatDateTime(new Date()));
        con.setDescription(clue.getDescription());
        con.setEmail(clue.getEmail());
        con.setFullname(clue.getFullname());
        con.setId(UUIDUtils.getUUID());
        con.setJob(clue.getJob());
        con.setMphone(clue.getMphone());
        con.setNextContactTime(clue.getNextContactTime());
        con.setOwner(user.getId());
        con.setSource(clue.getSource());
        con.setCustomerId(c.getId());

        //注入客户的mapper，调用新建客户方法插入数据
        contactsMapper.insertContacts(con);

        //注入线索备注mapper，根据clueID查询该线索下所有的备注
        List<ClueRemark> crList = clueRemarkMapper.selectClueRemarkByClueId(clueId);

        //如果该线索下有备注，把该线索下所有的备注转换到客户备注表中一份
        //把该线索下所有的备注转换到联系人备注表中一份
        if (crList.size() > 0 && crList != null) {
            //遍历crList，封装客户备注
            CustomerRemark cur = null;
            ContactsRemark cor = null;
            List<CustomerRemark> curList = new ArrayList<CustomerRemark>();//客户备注的list集合
            List<ContactsRemark> corList = new ArrayList<ContactsRemark>();//联系人备注的list集合
            for (ClueRemark cr: crList) {
                cur = new CustomerRemark();
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setCustomerId(c.getId());
                cur.setEditBy(cr.getEditBy());
                cur.setEditFlag(cr.getEditFlag());
                cur.setEditTime(cr.getEditTime());
                cur.setId(UUIDUtils.getUUID());
                cur.setNoteContent(cr.getNoteContent());
                //封装客户备注
                curList.add(cur);
                //封装联系人备注
                cor = new ContactsRemark();
                cor.setContactsId(con.getId());
                cor.setCreateBy(cr.getCreateBy());
                cor.setCreateTime(cr.getCreateTime());
                cor.setEditBy(cr.getEditBy());
                cor.setEditFlag(cr.getEditFlag());
                cor.setEditTime(cr.getEditTime());
                cor.setId(UUIDUtils.getUUID());
                cor.setNoteContent(cr.getNoteContent());
                corList.add(cor);

            }
            //注入客户备注mapper，调用客户备注的mapper
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            //注入联系人备注的mapper，调用联系人备注的方法
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }
        //根据clueId查询线索和市场活动的关联关系
        //注入查询关联关系的mapper，调用其方法
        List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);

        //把该线索和市场活动的关联关系转换到联系人和市场活动的关联关系表中
        if (carList != null && carList.size() > 0) {
            ContactsActivityRelation coar = null;
            List<ContactsActivityRelation> coarList = new ArrayList<ContactsActivityRelation>();
            for (ClueActivityRelation car :
                    carList) {
                coar = new ContactsActivityRelation();

                coar.setId(car.getActivityId());
                coar.setContactsId(con.getId());
                coar.setActivityId(UUIDUtils.getUUID());
                coarList.add(coar);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(coarList);
        }
        //如果需要创建交易，则往交易表中添加一条记录，还需要把该线索下的备注转换到交易备注表中一份
        if ("true".equals(isCreateTran)) {
            Tran tran = new Tran();
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(con.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setCustomerId(c.getId());
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setOwner(user.getId());
            tran.setStage((String) map.get("stage"));
            //注入交易的mapper，调用其方法
            tranMapper.insertTran(tran);

            if (carList != null && carList.size() > 0) {
                TranRemark tr = null;
                List<TranRemark> trList = new ArrayList<TranRemark>();
                for (ClueRemark cr:crList) {
                    tr = new TranRemark();
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setEditBy(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setEditTime(cr.getEditTime());
                    tr.setId(UUIDUtils.getUUID());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setTranId(tran.getId());
                    trList.add(tr);
                }

                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }
        //删除线索下的备注
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);

        //删除线索和市场活动的关联关系
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);

        //删除线索
        clueMapper.deleteClueById(clueId);
    }
}
 */
        //把该线索中有关个人的信息转换到联系人表中
        Contacts co=new Contacts();
        co.setAddress(clue.getAddress());
        co.setAppellation(clue.getAppellation());
        co.setContactSummary(clue.getContactSummary());
        co.setCreateBy(user.getId());
        co.setCreateTime(DateUtils.formatDateTime(new Date()));
        co.setCustomerId(c.getId());
        co.setDescription(clue.getDescription());
        co.setEmail(clue.getEmail());
        co.setFullname(clue.getFullname());
        co.setId(UUIDUtils.getUUID());
        co.setJob(clue.getJob());
        co.setMphone(clue.getMphone());
        co.setNextContactTime(clue.getNextContactTime());
        co.setOwner(user.getId());
        co.setSource(clue.getSource());
        contactsMapper.insertContacts(co);

        //根据clueId查询该线索下所有的备注
        List<ClueRemark> crList=clueRemarkMapper.selectClueRemarkByClueId(clueId);

        //如果该线索下有备注，把该线索下所有的备注转换到客户备注表中一份,把该线索下所有的备注转换到联系人备注表中一份
        if(crList!=null&&crList.size()>0){
            //遍历crList，封装客户备注
            CustomerRemark cur=null;
            ContactsRemark cor=null;
            List<CustomerRemark> curList=new ArrayList<CustomerRemark>();
            List<ContactsRemark> corList=new ArrayList<ContactsRemark>();
            for(ClueRemark cr:crList){
                cur=new CustomerRemark();
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setCustomerId(c.getId());
                cur.setEditBy(cr.getEditBy());
                cur.setEditFlag(cr.getEditFlag());
                cur.setEditTime(cr.getEditTime());
                cur.setId(UUIDUtils.getUUID());
                cur.setNoteContent(cr.getNoteContent());
                curList.add(cur);

                cor=new ContactsRemark();
                cor.setContactsId(co.getId());
                cor.setCreateBy(cr.getCreateBy());
                cor.setCreateTime(cr.getCreateTime());
                cor.setEditBy(cr.getEditBy());
                cor.setEditFlag(cr.getEditFlag());
                cor.setEditTime(cr.getEditTime());
                cor.setId(UUIDUtils.getUUID());
                cor.setNoteContent(cr.getNoteContent());
                corList.add(cor);
            }
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }

        //根据clueId查询该线索和市场活动的关联关系
        List<ClueActivityRelation> carList=clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);

        //把该线索和市场活动的关联关系转换到联系人和市场活动的关联关系表中
        if(carList!=null&&carList.size()>0){
            ContactsActivityRelation coar=null;
            List<ContactsActivityRelation> coarList=new ArrayList<ContactsActivityRelation>();
            for(ClueActivityRelation car:carList){
                coar=new ContactsActivityRelation();
                coar.setActivityId(car.getActivityId());
                coar.setContactsId(co.getId());
                coar.setId(UUIDUtils.getUUID());
                coarList.add(coar);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(coarList);
        }

        //如果需要创建交易，则往交易表中添加一条记录,还需要把该线索下的备注转换到交易备注表中一份
        if("true".equals(isCreateTran)){
            Tran tran=new Tran();
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(co.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setCustomerId(c.getId());
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setOwner(user.getId());
            tran.setStage((String) map.get("stage"));
            tranMapper.insertTran(tran);

            if(crList!=null&&crList.size()>0){
                TranRemark tr=null;
                List<TranRemark> trList=new ArrayList<TranRemark>();
                for(ClueRemark cr:crList){
                    tr=new TranRemark();
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setEditBy(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setEditTime(cr.getEditTime());
                    tr.setId(UUIDUtils.getUUID());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setTranId(tran.getId());
                    trList.add(tr);
                }

                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }

        //删除该线索下所有的备注
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);

        //删除该线索和市场活动的关联关系
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);

        //删除线索
        clueMapper.deleteClueById(clueId);
    }
}

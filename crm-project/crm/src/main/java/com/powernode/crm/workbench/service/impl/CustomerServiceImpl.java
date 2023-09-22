package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.mapper.CustomerMapper;
import com.powernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title:CustomerServiceImpl Author liu
 * @Date:2023/4/21 16:56
 * @Version 1.0
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public List<String> queryCustomerNameByName(String name) {
        return customerMapper.selectCustomerNameByName(name);
    }
}

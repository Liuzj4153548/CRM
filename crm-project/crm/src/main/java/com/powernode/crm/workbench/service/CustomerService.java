package com.powernode.crm.workbench.service;

import java.util.List;

/**
 * @title:CustomerService Author liu
 * @Date:2023/4/21 16:55
 * @Version 1.0
 */
public interface CustomerService {
    List<String> queryCustomerNameByName(String name);
}

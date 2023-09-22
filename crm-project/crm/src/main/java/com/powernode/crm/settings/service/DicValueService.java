package com.powernode.crm.settings.service;

import com.powernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @title:DicValueService Author liu
 * @Date:2023/4/14 16:51
 * @Version 1.0
 */
public interface DicValueService {
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}

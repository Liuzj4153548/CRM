package com.powernode.crm.settings.service.impl;

import com.powernode.crm.settings.domain.DicValue;
import com.powernode.crm.settings.mapper.DicValueMapper;
import com.powernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title:DicValueServiceImpl Author liu
 * @Date:2023/4/14 16:52
 * @Version 1.0
 */
@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    private DicValueMapper dicValueMapper;

    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDirValueByTypeCode(typeCode);
    }
}

package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.ClueActivityRelation;
import com.powernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.powernode.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title:ClueActivityRelationServiceImpl Author liu
 * @Date:2023/4/17 0:12
 * @Version 1.0
 */
@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(relation);
    }
}

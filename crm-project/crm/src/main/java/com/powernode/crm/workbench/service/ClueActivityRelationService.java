package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

/**
 * @title:ClueActivityService Author liu
 * @Date:2023/4/17 0:10
 * @Version 1.0
 */
public interface ClueActivityRelationService {
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list);

    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation clueActivityRelation);
}

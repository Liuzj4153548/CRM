package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.Clue;

import java.util.Map;

/**
 * @title:ClueService Author liu
 * @Date:2023/4/14 17:58
 * @Version 1.0
 */
public interface ClueService {
    int saveCreateClue(Clue clue);

    Clue queryClueForDetailById(String id);

    void saveConvertClue(Map<String,Object> map);


}

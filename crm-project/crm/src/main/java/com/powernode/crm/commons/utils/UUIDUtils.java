package com.powernode.crm.commons.utils;

import java.util.UUID;

/**
 * @title:UUIDUtils Author liu
 * @Date:2023/3/30 22:40
 * @Version 1.0
 */


public class UUIDUtils {

    /**
     * 获取UUID的值
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

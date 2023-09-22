package com.powernode.crm.uuid;

import java.util.UUID;

/**
 * @title:UUID Author liu
 * @Date:2023/3/30 22:32
 * @Version 1.0
 */

public class UUIDTest {
    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(uuid);
    }
}

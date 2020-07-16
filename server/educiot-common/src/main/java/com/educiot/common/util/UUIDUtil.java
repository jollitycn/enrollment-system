package com.educiot.common.util;

import java.util.UUID;

/**
 * @author ：liuhao
 * @date ：Created in 2020/1/14
 */
public class UUIDUtil {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}

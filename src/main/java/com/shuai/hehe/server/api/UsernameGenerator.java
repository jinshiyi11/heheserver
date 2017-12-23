package com.shuai.hehe.server.api;

import java.util.Random;

/**
 *
 */
public class UsernameGenerator {
    private static Random mRandom=new Random();
    private final static String[] NAMES={"明明","天天","星星","兴兴","丽丽","莎莎","亮亮","小雷","小芳"};

    public static String generate(){
        int index=mRandom.nextInt(NAMES.length);
        return NAMES[index];
    }
}

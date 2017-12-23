package com.shuai.hehe.server.api;

import java.util.Random;

/**
 *
 */
public class UserHeadImageGenerator {
    private static Random mRandom=new Random();
    private final static String[] IMAGES ={
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=221435460,1896812&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1674942956,1719219321&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3533844229,1587972444&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4162474333,373314887&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1295633504,4204207108&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1172295591,1191991812&fm=27&gp=0.jpg"};

    public static String generate(){
        int index=mRandom.nextInt(IMAGES.length);
        return IMAGES[index];
    }
}

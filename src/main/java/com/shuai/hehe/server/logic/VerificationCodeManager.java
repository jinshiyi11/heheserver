package com.shuai.hehe.server.logic;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class VerificationCodeManager {
    private static VerificationCodeManager mSelf;
    private Map<String, String> mCodeMap = new HashMap<>();

    public static synchronized VerificationCodeManager getInstance() {
        if (mSelf == null) {
            mSelf = new VerificationCodeManager();
        }

        return mSelf;
    }

    private VerificationCodeManager() {
    }

    public void generateVerificationCode(String phone) {
        //todo

    }

    public boolean isValid(String phone, String verificationCode) {
        return true;
    }
}

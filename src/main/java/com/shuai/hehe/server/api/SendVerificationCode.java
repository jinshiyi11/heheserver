package com.shuai.hehe.server.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.shuai.hehe.server.exception.ServiceException;
import com.shuai.hehe.server.response.ErrorCode;
import com.shuai.hehe.server.response.ResponseInfo;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 发送验证码
 */
@RestController
public class SendVerificationCode {
    private final String AUTH_NAME = "api";
    private final String AUTH_PASSWORD = "96b865b717015fc26f3cbc8bc049c167";
    private final OkHttpClient client = new OkHttpClient();

    private static class SendVerificationCodeResult {
        @SerializedName("error")
        public int errorCode;

        @SerializedName("msg")
        public String message;
    }

    @GetMapping("/api/sendVerificationCode")
    @ResponseBody
    public ResponseInfo sendVerificationCode(@RequestParam("mobile") String phoneNumber) {
        //TODO:防攻击，异常处理,检查参数
        ResponseInfo result = new ResponseInfo();
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("mobile", phoneNumber)
                    .add("message", "验证码：286221，请在10分钟内完成验证【铁壳测试】")
                    .build();

            String credential = Credentials.basic(AUTH_NAME, AUTH_PASSWORD);
            Request request = new Request.Builder()
                    .url("http://sms-api.luosimao.com/v1/send.json")
                    .header("Authorization", credential)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            Gson gson = new Gson();
            SendVerificationCodeResult sendResult = gson.fromJson(body, SendVerificationCodeResult.class);

            if (sendResult.errorCode != 0) {
                throw new ServiceException(
                        ErrorCode.ERROR_SEND_VERIFICATION_CODE.getErrorCode(),
                        String.format("%s:%d", sendResult.message, sendResult.errorCode)
                );
            }
        }catch (IOException exception){
            throw new ServiceException(
                    ErrorCode.ERROR_SEND_VERIFICATION_CODE.getErrorCode(),exception.getMessage());
        }
        return result;
    }

}

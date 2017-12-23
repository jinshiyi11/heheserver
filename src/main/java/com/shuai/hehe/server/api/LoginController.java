package com.shuai.hehe.server.api;

import com.shuai.hehe.server.entity.User;
import com.shuai.hehe.server.mapper.UserMapper;
import com.shuai.hehe.server.response.ResponseInfo;
import okhttp3.OkHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@RestController
public class LoginController {
    private static final Log mLogger = LogFactory.getLog(LoginController.class);

//    @Autowired
//    private SessionAuthenticationStrategy sas;

    @Autowired
    private UserMapper mUserMapper;
    private final OkHttpClient mClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public static class TokenInfo {
        public String token;
        public int expiresIn;
        public String uid;
    }

    private static class OAuth2AccessToken {
        public String access_token;
        public String token_type;
        public int expires_in;
        public String scope;
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseInfo<TokenInfo> login(
            @RequestParam("username") String phone,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

//        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(phone, password);
//        Authentication authentication=authenticationManager.authenticate(authToken);
////        sas.onAuthentication(authentication, request, response);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

//        RequestBody formBody = new FormBody.Builder()
//                .add("username", phone)
//                .add("password", password)
//                .add("grant_type", "password")
//                .build();
//
//        String credential = Credentials.basic("ClientId", "secret");
//        Request httpRequest = new Request.Builder()
//                .url("http://" + request.getServerName() + ":"
//                        + request.getServerPort() + "/oauth/token")
//                .header("Authorization", credential)
//                .post(formBody)
//                .build();
//        Response httpResponse = mClient.newCall(httpRequest).execute();
//        if (httpResponse.code() == HttpServletResponse.SC_OK) {
//            String body = httpResponse.body().string();
//            Gson gson = new Gson();
//            OAuth2AccessToken token = gson.fromJson(body, OAuth2AccessToken.class);
//
//            ResponseInfo<TokenInfo> result = new ResponseInfo<>();
//            TokenInfo tokenInfo = new TokenInfo();
//            tokenInfo.token = token.access_token;
//            tokenInfo.expiresIn = token.expires_in;
//            User user = mUserMapper.getByPhone(phone);
//            tokenInfo.uid = String.valueOf(user.getId());
//            result.setData(tokenInfo);
//            return result;
//        }

        String url = "http://" + request.getServerName() + ":"
                + request.getServerPort() + "/oauth/token";
        mLogger.info("login url:" + url);
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(url);
        resource.setClientId("ClientId");
        resource.setClientSecret("secret");
        resource.setGrantType("password");

        resource.setUsername(phone);
        resource.setPassword(password);
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource,
                new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
        restTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
        org.springframework.security.oauth2.common.OAuth2AccessToken token = restTemplate.getAccessToken();
        ResponseInfo<TokenInfo> result = new ResponseInfo<>();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.token = token.getValue();
        tokenInfo.expiresIn = token.getExpiresIn();
        User user = mUserMapper.getByPhone(phone);
        tokenInfo.uid = String.valueOf(user.getId());
        result.setData(tokenInfo);

        return result;
    }
}

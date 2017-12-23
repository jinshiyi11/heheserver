package com.shuai.hehe.server.api;

import com.shuai.hehe.server.entity.User;
import com.shuai.hehe.server.logic.VerificationCodeManager;
import com.shuai.hehe.server.mapper.UserMapper;
import com.shuai.hehe.server.response.ResponseInfo;
import com.shuai.hehe.server.response.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@RestController
public class RegisterController {
    @Autowired
    private ApplicationContext mContext;

    @Autowired
    private UserMapper mUserMapper;

    @PostMapping("/api/registerByPhone")
    @ResponseBody
    public ResponseInfo register(@RequestParam("username") String phone,
                                 @RequestParam("password") String password,
                                 @RequestParam("verificationCode") String verificationCode,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        if (!VerificationCodeManager.getInstance().isValid(phone, verificationCode)) {
            return new ResponseInfo(ErrorCode.ERROR_INVALID_VERIFICATION_CODE);
        }

        User user = mUserMapper.getByPhone(phone);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
            String md5Password = passwordEncoder.encodePassword(password,null);
            user.setPassword(md5Password);
            user.setNickName(UsernameGenerator.generate());
            user.setHeadImageUrl(UserHeadImageGenerator.generate());
            mUserMapper.addUser(user);

            LoginController login=mContext.getBean(LoginController.class);
            return login.login(phone,password,request,response);
        }

        return new ResponseInfo(ErrorCode.ERROR_USER_ALREADY_EXIST);
    }
}

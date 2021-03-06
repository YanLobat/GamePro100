package com.hackday;

import com.hackday.entity.UserEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Constants {
    public static final Long ROLE_USER_ID = 5L;
    public static final String ROLE_USER_LOGIN = "test_user";
    public static final String ROLE_USER_PASSWORD = "qwerty";

    public static final Long ROLE_ADMIN_ID = 4L;
    public static final String ROLE_ADMIN_LOGIN = "test_admin";
    public static final String ROLE_ADMIN_PASSWORD = "qwerty";

    public static void loginRoleUser() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(Constants.ROLE_USER_LOGIN, Constants.ROLE_USER_PASSWORD);
        UserEntity user = new UserEntity();
        user.setLogin(ROLE_USER_LOGIN);
        user.setId(ROLE_USER_ID);
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void loginRoleAdmin() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(Constants.ROLE_ADMIN_LOGIN, Constants.ROLE_ADMIN_PASSWORD);
        UserEntity user = new UserEntity();
        user.setLogin(ROLE_ADMIN_LOGIN);
        user.setId(ROLE_ADMIN_ID);
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void loginRoleAnonymous() {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("GUEST","USERNAME", AuthorityUtils
                .createAuthorityList("ROLE_ANONYMOUS")));
    }

    public static String randomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}

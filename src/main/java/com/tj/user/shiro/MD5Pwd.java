package com.tj.user.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by tym on 2019/7/22 0022.
 */
public class MD5Pwd {

    public static final String ALGORITHM_NAME = "MD5";  // 基础散列算法
    public static final int HASH_ITERATIONS = 2;        // 自定义散列次数

    /**
     * MD5加密
     * @param username
     * @param pwd
     * @return
     */
    public static String MD5Pwd(String username, String pwd) {
        // salt盐值 username
        ByteSource salt = ByteSource.Util.bytes(username);
        String md5Pwd = new SimpleHash(ALGORITHM_NAME, pwd,
                salt, HASH_ITERATIONS).toHex();
        return md5Pwd;
    }


}

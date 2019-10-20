package com.tj.user.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tj.service.HappysysUserClientService;
import com.tj.user.HappysysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证和授权
 * Created by tym on 2019/5/4 0004.
 */
public class UsersRealm extends AuthorizingRealm {

    @Autowired
    private HappysysUserClientService userClientService;



    /**
     * 加密
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("鉴权：");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //调用service根据用户名查询数据库获得Users对象
        HappysysUser user = userClientService.findbyname(token.getUsername());
        if(user==null){
            //抛出UnknownAccountException
            return null;
        }
        //第三个参数：盐值(这个盐是 username)
        ByteSource solt = ByteSource.Util.bytes(user.getUserName());
        //第四个参数：获取这个Realm的信息
        String name = this.getName();
        System.out.println(solt+"\t"+name);
        return new SimpleAuthenticationInfo(user.getUserName(),user.getUserPassword(),solt,name);
    }
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权："+userClientService);

       /* SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        HappysysUser user = service.getOne(new QueryWrapper<HappysysUser>().eq("user_name",username));

        info.addStringPermission(user.getPermission());
        info.addRole(user.getRoles());
        System.out.println("permission:"+user.getPermission());
        System.out.println("orders:"+user.getRoles());

        return info;*/
       return  new SimpleAuthorizationInfo();
    }
}

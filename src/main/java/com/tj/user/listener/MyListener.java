package com.tj.user.listener;

import com.tj.user.HappysysUser;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class MyListener implements ServletContextListener,HttpSessionListener {
    //application只有一个
    private ServletContext application;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("application初始化");
        application=sce.getServletContext();
        application.setAttribute("users",new ArrayList<String>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("application销毁");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        //session.setMaxInactiveInterval(10);
        System.out.println("创建sessionid:"+session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session=se.getSession();
        System.out.println("销毁sessionid:"+session.getId());
        //销毁
        HappysysUser user=(HappysysUser) session.getAttribute("user");

        if(user != null){//用户登陆了才销毁
            ServletContext application = session.getServletContext();

            List<String> userNameList=(List<String>)application.getAttribute("users") ;
            for (String userName :userNameList){
                if(userName.equals(user.getUserName())){
                    System.out.println("application usrName："+userName+"\tuser.getUserName():"+user.getUserName());
                    userNameList.remove(userName);
                    break;
                }
            }
        }

    }
}

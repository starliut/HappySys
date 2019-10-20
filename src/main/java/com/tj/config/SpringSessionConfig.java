package com.tj.config;

import com.tj.user.listener.MyListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringSessionConfig {

    /**
     *        解决springBoot集成springSession导致 session监听器失效问题
     */
    @Bean
    public SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter2() {
        List<HttpSessionListener> httpSessionListeners = new ArrayList<HttpSessionListener>();
        httpSessionListeners.add(new MyListener());
        return new SessionEventHttpSessionListenerAdapter(httpSessionListeners);
    }


}

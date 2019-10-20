package com.tj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @program: happysys
 * @Date: 2019/7/11 11:52
 * @Author: Mr.Deng
 * @Description:
 */

@EnableRedisHttpSession(
        maxInactiveIntervalInSeconds = 1000	//单位：秒
        )
@ServletComponentScan("com.tj.user.listener")  //扫描监听器
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.tj.service"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Consumer_Pre_80 {

 public static void main(String[] args) {
  SpringApplication.run(Consumer_Pre_80.class,args);
 }

}

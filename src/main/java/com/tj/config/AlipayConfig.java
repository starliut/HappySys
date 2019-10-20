package com.tj.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100200645206";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
 public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCkzbYMrd5VPTdckirXKnt0xWmwxGi+NJ1OO4OtI93sXiIxP4cwzFnBhr0/NDhjnsABsQaW5JhuS1pysYCaUI1FO1pv+0Uyl7wRqs280P8gingeOQDP17HTAnB8mu/OJVnYIlZfpJISNdTKWRn/ogQQYJ+xT6Wb6JEBlYf2O4tUloQI5j8K73wK49cof0nlMB0JaOjKutxsatVd+OSkduHQotP9Vn+V+eBTDRApOnr97NpjkQ8VNzxBLric6wVcrch6s9FwfYh5eoHWRPO5agg6yGN/YZfs7ON/Ipaz6D5JtDydMGqyJx1x13295Y7sVww01q4maJLa7n9Edh3ilYGHAgMBAAECggEARMYHw7DHCIE8WigxMotq66b6XScJot5oCt6xK469LMd3s1+UdhvBFQ66aObM5V25OLt8SXnp1pUhkJ1cUols+sgzDGoqTVuewyypILOC1DyUteAJ47JaZfktogIyMq/Jl3V04ALYZjsKqsqWG+Srl34LBj57mN1rreKN04aztT9R/ebre3wwdnY4JafN9+7Ve2NZWbd/6eI26sV6SKouYRXVmo92W3r5bROgzGtFhj65peKFoAney0FZ+Nzm1kiy4STgYdV7pW+Nr8HQ4/U7CTwF3pR4PAUrwlNWRALJJoXs2Xihmlu0Lt1h2Wm0JyX7Tb5nrZD1ij+jXTyEYq8byQKBgQDnjGwVj9bhl/L9fcsj+xZl/gwgl/7PYNRh6ki0GW+KXWZbF++gMMiLYI3TMNrk0G/S+iHKzA914+fRs9BF9XYi4wL3TcgSOQwQLYcmQZm/hmf/AmjGRBB+xQT+8xBBfCSYdmloWD9YFR88yHwTzF+6sFBztCUUcP2KMnaQg7vp5QKBgQC2NO+RKcZ3LC2ydPcxAlPGNM/v7Gke2GdB3DQpojqeMbhceVWacktWDSLvgJrzjZcW7QNwQY8zUbvvHiIBPj3Sv78kAHz3hVKfm1xOm4EHX5azfCxSdvc5AkZS+BuIAl9M160/mNLdKRwt/KIWgEGUj2A4hLKmC1B8FlCGJG+W+wKBgQDiX16sFdw24Z8mRP19R9U2EZE5rF/pzvjUYYCC0vtBgt624EfdjNQDr3n4pyLZ4Q3ybajr42SaFR5m3i0YuEmvAdG8hca8ShmJF3mx5SCzJmw1290jORgHWAbyWh/iZcRBakLTSLNlIgFQI5FqqbxNgGKnO8+klHLL0VopHyFwIQKBgHruwkflsKLNKtPBuNnXISCUl0rf7ZjDFv/Hi2Yfr372OtYhgxEeVhfr6ws+cM0sValBJlXB1dUhKzRM11KRZPLhLhwjPC1gRXusPZ5HqsmHw8y7VcgZvfk3wKzP+vtL5nKY/anv+S0ARKP+VHnr9NM/3QKk/UesUJQ4FgI7aRKrAoGBAMtvENaeFjDlYTXXQd5U2IxTII5uglUkIGlTkYwHPLADohR1WyTGEsFJa00RKIKM7v2X6xZgSJ/8JgV322R81e9O4/1lFXwC6RIGenE2usgnD998sncRvM+knAvf7/IqQUBdE7qxbpKLcE0SRXw+8bWD6C3Cg3ippuGJNLBHe82O";

 // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
 public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3t5NxWramisxRb5nsriIkcb3CDgs403wR7qIHm4lhPwpL80VglwuvbVzUkEy3UWORNZTzzVBvuKGzsOnfc1ReNopgQuHrGT7d3GDKqJNBIaoNvYO2oi6wAHJ6uo8AmKHDu7jyd+psJBM5p8ItW5qvDIzulWamMmvRcl4Nzo2CDrzheUUlL8BHKAV8GJ2NsIMn0fUpQ9OtZSCDZme8Z1Y67kyzZ1wqyoeEleFr7BqAZIizrd29HPqdTu5p91dockrgX8KVU2v2j0qNcvsfVrClzuY+40MBScVSXoBkl5upB39YGeBZXnLDws0IQub7vLkwRKuukZsGgRaNy2WHJjhwIDAQAB";

 // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
 public static String notify_url = "http://localhost:800/notify_url";

 // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
 public static String return_url = "http://localhost:800/return_url";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


package com.tj.user.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HttpClientUtil {
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).setConnectionRequestTimeout(15000).build();
    private static HttpClientUtil instance = null;

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            instance = new HttpClientUtil();
        }

        return instance;
    }

    public String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);
        return this.sendHttpPost(httpPost, "utf-8");
    }

    public String sendHttpPost(String httpUrl, Map<String, String> maps, String type) {
        HttpPost httpPost = new HttpPost(httpUrl);
        List<NameValuePair> nameValuePairs = new ArrayList();
        Iterator var6 = maps.keySet().iterator();

        while(var6.hasNext()) {
            String key = (String)var6.next();
            nameValuePairs.add(new BasicNameValuePair(key, (String)maps.get(key)));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, type));
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return this.sendHttpPost(httpPost, type);
    }

    private String sendHttpPost(HttpPost httpPost, String reponseType) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;

        try {
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(this.requestConfig);
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, reponseType);
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return responseContent;
    }

    public String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return this.sendHttpGet(httpGet);
    }

    public String sendHttpsGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return this.sendHttpsGet(httpGet);
    }

    public int sendMsgUtf8(String Uid, String Key, String content, String mobiles) {
        Map maps = new HashMap();
        maps.put("Uid", Uid);
        maps.put("Key", Key);
        maps.put("smsMob", mobiles);
        maps.put("smsText", content);
        String result = this.sendHttpPost("http://utf8.sms.webchinese.cn", maps, "utf-8");
        return Integer.parseInt(result);
    }

    public int sendMsgGbk(String Uid, String Key, String content, String mobiles) {
        Map maps = new HashMap();
        maps.put("Uid", Uid);
        maps.put("Key", Key);
        maps.put("smsMob", mobiles);
        maps.put("smsText", content);
        String result = this.sendHttpPost("http://gbk.sms.webchinese.cn", maps, "gbk");
        return Integer.parseInt(result);
    }

    private String sendHttpGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;

        try {
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(this.requestConfig);
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

        return responseContent;
    }

    private String sendHttpsGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;

        try {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(this.requestConfig);
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return responseContent;
    }

    public String getErrorMsg(int errorCode) {
        if (errorCode == -1) {
            return "没有该用户账户";
        } else if (errorCode == -2) {
            return "接口密钥不正确";
        } else if (errorCode == -3) {
            return "短信数量不足";
        } else if (errorCode == -4) {
            return "手机号格式不正确";
        } else if (errorCode == -21) {
            return "MD5接口密钥加密不正确";
        } else if (errorCode == -11) {
            return "该用户被禁用";
        } else if (errorCode == -14) {
            return "短信内容出现非法字符";
        } else if (errorCode == -41) {
            return "手机号码为空";
        } else if (errorCode == -42) {
            return "短信内容为空";
        } else if (errorCode == -51) {
            return "短信签名格式不正确";
        } else {
            return errorCode == -6 ? "IP限制" : "未知错误码:" + errorCode;
        }
    }
}

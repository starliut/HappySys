package com.tj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @program: HappySys
 * @Date: 2019/8/12 17:44
 * @Author: Mr.Deng
 * @Description:
 */
public class UUIDUtil {

 public static String getOrderIdByUUId() {
  int machineId = 1;//最大支持1-9个集群机器部署
  int hashCodeV = UUID.randomUUID().toString().hashCode();
  if(hashCodeV < 0) {//有可能是负数
   hashCodeV = - hashCodeV;
  }
//         0 代表前面补充0
//         4 代表长度为4
//         d 代表参数为正数型
  return  machineId+ String.format("%015d", hashCodeV);
 }


 public static String getOrderIdByTime() {
  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
  String newDate=sdf.format(new Date());
  try {
   Date parse = sdf.parse(newDate);
   System.out.println(parse);
  } catch (ParseException e) {
   e.printStackTrace();
  }
  String result = "";
  Random random=new Random();
  for(int i=0;i<3;i++){
   result+=random.nextInt(10);
  }
  return newDate+result;
 }

}

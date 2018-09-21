package cn.zjs.main;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * @author: zjs
 * @Date: 2018/9/21
 * @Description: Convert a IPV4 address String to a 32 bit integer  2885681152
 */
public class IpUtils2 {

    /*
    Convert a IPV4 address String to a 32 bit integer
     */
    public static BigInteger ipv4TransToInt(String ip) {
        long result = 0l;
        if (StringUtils.isBlank(ip)) {
            throw new IllegalArgumentException("Invalid format:no content" + ip);
        }
        String[] arr = ip.split("\\.");
        if(arr.length!=4){
            throw new IllegalArgumentException("Invalid format" + ip);
        }
        for(int i=0;i<arr.length;i++){
            String seg = arr[i].trim();
            if(!isNumeric(seg)){
                throw new IllegalArgumentException("Invalid format" + ip);
            }
            if(Long.parseLong(seg)>255){
                throw new IllegalArgumentException("Ip segment is out of range "+ip);
            }
            result += Long.parseLong(seg) <<(8*(3 - i));
        }
        return BigInteger.valueOf(result);
    }

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(ipv4TransToInt("172.168.5.1"));
    }
}

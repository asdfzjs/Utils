package cn.zjs.main;

import org.apache.commons.lang3.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.math.BigInteger;

/**
 * @author: zjs
 * @Date: 2018/9/21
 * @Description: Convert a IPV4 address String to a 32 bit integer
 */
public class IpUtils {

    private static Boolean INIT_STATUS = Boolean.TRUE;
    private static Boolean CONTAINS_DIGIT = Boolean.FALSE;
    private static Boolean START_SPACE = Boolean.FALSE;
    private static Boolean END_SPACE = Boolean.FALSE;
    private static Boolean NEW_SEGMENT = Boolean.TRUE;

    private static int section_count = 0;
    private static long result = 0l;
    private static long ele= 0l;

    /*
    Convert a IPV4 address String to a 32 bit integer
     */
    public static BigInteger ipv4TransToInt(String ip) {
        init();
        if(StringUtils.isBlank(ip)){
            throw new IllegalArgumentException("Invalid format:no content"+ip);
        }
        for(int i=0;i<ip.length();i++){
            char c = ip.charAt(i);
            if(is_digit(c)){
                if(NEW_SEGMENT || CONTAINS_DIGIT || START_SPACE){
                    if(ele*10>255){
                        throw new IllegalArgumentException("Invalid format"+ip);
                    }
                    ele=ele*10+Long.parseLong(c+"");
                    NEW_SEGMENT = Boolean.FALSE;
                    CONTAINS_DIGIT = Boolean.TRUE;
                    START_SPACE = Boolean.FALSE;
                }else{
                    throw new IllegalArgumentException("Invalid format"+ip);
                }
            }else if(c == ' '){
                if(INIT_STATUS){
                    throw new IllegalArgumentException("Invalid format:can not start with space"+ip);
                }
                if(START_SPACE || END_SPACE){
                }else if(CONTAINS_DIGIT){
                    END_SPACE = Boolean.TRUE;
                    CONTAINS_DIGIT = Boolean.FALSE;
                }else if(NEW_SEGMENT){
                    START_SPACE = Boolean.TRUE;
                    NEW_SEGMENT = Boolean.FALSE;
                }else {
                    throw new IllegalArgumentException("Invalid format"+ip);
                }
            }else if(c == '.'){
                if(INIT_STATUS){
                    throw new IllegalArgumentException("Invalid format:can not start with dot "+ip);
                }
                if(CONTAINS_DIGIT || END_SPACE){
                    NEW_SEGMENT = Boolean.TRUE;
                    CONTAINS_DIGIT = Boolean.FALSE;
                    END_SPACE = Boolean.FALSE;
                    if(ele>255){
                        throw new IllegalArgumentException("Ip segment is out of range "+ip);
                    }
                    result += ele <<(8*(3 - section_count));
                    section_count++;
                    ele = 0l;
                }else {
                    throw new IllegalArgumentException("Invalid format"+ip);
                }
            }else{
                throw new IllegalArgumentException("Invalid format"+ip);
            }
            INIT_STATUS=Boolean.FALSE;
        }
        if(section_count!=3){
            throw new IllegalArgumentException("Invalid format"+ip);
        }
        if(START_SPACE || END_SPACE || NEW_SEGMENT){
            throw new IllegalArgumentException("Invalid format"+ip);
        }
        if(ele>255){
            throw new IllegalArgumentException("Ip segment is out of range "+ip);
        }
        result += ele <<(8*(3 - section_count));
        return BigInteger.valueOf(result);
    }

    public  static boolean is_digit(char c){
        if (!Character.isDigit(c)){
            return false;
        }
        return true;
    }

    public static void init(){
        INIT_STATUS = Boolean.TRUE;
        CONTAINS_DIGIT = Boolean.FALSE;
        START_SPACE = Boolean.FALSE;
        END_SPACE = Boolean.FALSE;
        NEW_SEGMENT = Boolean.TRUE;
        section_count = 0;
        result = 0l;
        ele = 0l;
    }

    public static void main(String[] args) {
        System.out.println(ipv4TransToInt("172.168.5.1"));
    }
}

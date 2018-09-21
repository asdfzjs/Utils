package cn.zjs.main;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author: zjs
 * @Date: 2018/9/21
 * @Description: Convert a IPV4 address String to a 32 bit integer  testCase
 */
public class IpUtilsTest {

    /*
    test error argument exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIpBlank() {
        IpUtils.ipv4TransToInt(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpNull() {
        IpUtils.ipv4TransToInt(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpSegmentWithSpace() {
        IpUtils.ipv4TransToInt("17 2.168.5.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpSegmentOutOfRange() {
        IpUtils.ipv4TransToInt("256.168.5.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpWithRegex1() {
        IpUtils.ipv4TransToInt("168.5.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpWithRegex2() {
        IpUtils.ipv4TransToInt("a.168.5.1");
    }

    @Test
    public void testIpSegmentWithSpace2() {
        Assert.assertEquals(BigInteger.valueOf(2896692481L), IpUtils.ipv4TransToInt("172 . 168 .5 . 1"));
        Assert.assertEquals(BigInteger.valueOf(2896692481L), IpUtils.ipv4TransToInt("172.168.5.1"));
        Assert.assertEquals(BigInteger.valueOf(2896692481L), IpUtils.ipv4TransToInt("172 .168 .5 . 1"));
        Assert.assertEquals(BigInteger.valueOf(2896692481L), IpUtils.ipv4TransToInt("172 . 168 .5 . 1"));
        Assert.assertEquals(BigInteger.valueOf(2896692481L), IpUtils.ipv4TransToInt("172. 168.5 . 1"));
    }

    //normal
    @Test
    public void testIpv4ToInt() {
        Assert.assertEquals(BigInteger.valueOf(2896692481L), IpUtils.ipv4TransToInt("172.168.5.1"));
    }


}

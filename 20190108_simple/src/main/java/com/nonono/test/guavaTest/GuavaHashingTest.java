package com.nonono.test.guavaTest;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.springframework.util.Base64Utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * guava hashing测试
 */
public class GuavaHashingTest {

    public void test() {
        testHmacSHA1();
        testMD5();
        testJavaHmacSHA1();
        testByte();
    }

    private void testHmacSHA1() {
        String encryText = "加达里";
        String encryKey = "FEDS78%C1";
        SecretKey secretKey = new SecretKeySpec(encryKey.getBytes(Charsets.UTF_8), "HmacSHA1");
        Hasher hasher = Hashing.hmacSha1(secretKey).newHasher();
        HashCode hashCode = hasher.putString(encryText, Charsets.UTF_8).hash();
        System.out.println("hmacSha1:" + hashCode.toString());
    }

    private void testMD5() {
        String encryText = "加达里";
        String encryKey = "FEDS78%C1";
        SecretKey secretKey = new SecretKeySpec(encryKey.getBytes(Charsets.UTF_8), "HmacMD5");
        Hasher hasher = Hashing.hmacMd5(secretKey).newHasher();
        HashCode hashCode = hasher.putString(encryText, Charsets.UTF_8).hash();
        System.out.println("hmacMd5:" + hashCode.toString());
    }

    private void testJavaHmacSHA1() {
        String encryText = "加达里";
        String encryKey = "FEDS78%C1";

        try {
            SecretKey secretKey = new SecretKeySpec(encryKey.getBytes(Charsets.UTF_8), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            byte[] text = encryText.getBytes(Charsets.UTF_8);
            byte[] results = mac.doFinal(text);
            System.out.println("JavaHmacSHA1 as base64:" + Base64Utils.encodeToString(results));
            System.out.println("JavaHmacSHA1 as hex:" + this.byteToHex(results));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    private String byteToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }

        return sb.toString();
    }

    private void testByte() {
        String str = "加达里";
        byte[] bytes = str.getBytes(Charsets.UTF_8);

        System.out.println("bytes:" + bytes);
        System.out.println("hex:" + byteToHex(bytes));
        System.out.println("String:" + new String(bytes));
    }
}

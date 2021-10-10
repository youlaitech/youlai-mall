package com.youlai.gateway.kaptcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 验证码文本生成器
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/4
 */
public class KaptchaTextCreator extends DefaultTextCreator {

    private static final String[] CNUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(",");
    private SecureRandom rand = SecureRandom.getInstanceStrong();

    public KaptchaTextCreator() throws NoSuchAlgorithmException {
    }

    @Override
    public String getText() {
        Integer result = 0;
        int x = this.rand.nextInt(10);
        int y = this.rand.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int randomoperands = (int) Math.round(rand.nextDouble() * 2);
        if (randomoperands == 0) {
            result = x * y;
            suChinese.append(CNUMBERS[x]);
            suChinese.append("*");
            suChinese.append(CNUMBERS[y]);
        } else if (randomoperands == 1) {
            if (!(x == 0) && y % x == 0) {
                result = y / x;
                suChinese.append(CNUMBERS[y]);
                suChinese.append("/");
                suChinese.append(CNUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append("+");
                suChinese.append(CNUMBERS[y]);
            }
        } else if (randomoperands == 2) {
            if (x >= y) {
                result = x - y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append("-");
                suChinese.append(CNUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(CNUMBERS[y]);
                suChinese.append("-");
                suChinese.append(CNUMBERS[x]);
            }
        } else {
            result = x + y;
            suChinese.append(CNUMBERS[x]);
            suChinese.append("+");
            suChinese.append(CNUMBERS[y]);
        }
        suChinese.append("=?@" + result);
        return suChinese.toString();
    }

}

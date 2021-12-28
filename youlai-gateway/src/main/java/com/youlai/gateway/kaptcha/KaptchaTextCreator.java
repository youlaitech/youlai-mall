package com.youlai.gateway.kaptcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 验证码文本生成器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/10/4
 */
public class KaptchaTextCreator extends DefaultTextCreator {

    private static final String[] CNUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(",");

    // https://gitee.com/youlaitech/youlai-mall/issues/I4E0WL?from=project-issue
    // private SecureRandom random = SecureRandom.getInstanceStrong(); // /dev/random 作为熵池，熵越大随机性越好，熵池数量不足就会阻塞线程，适用随机数比较高的请求。

    private SecureRandom random = new SecureRandom(); // /dev/urandom 作为熵池，非阻塞的随机数生成器，重复使用熵池中的数据以产生伪随机数据，不会产生阻塞，适用生成较低强度的伪随机数。

    public KaptchaTextCreator() throws NoSuchAlgorithmException {
    }

    @Override
    public String getText() {
        Integer result = 0;
        int x = this.random.nextInt(10);
        int y = this.random.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int randomoperands = (int) Math.round(random.nextDouble() * 2);
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

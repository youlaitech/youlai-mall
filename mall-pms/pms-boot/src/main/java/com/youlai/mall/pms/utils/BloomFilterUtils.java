package com.youlai.mall.pms.utils;

import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author DaniR
 * @version 1.0
 * @description 布隆过滤器，摘录自Google-guava包
 * @createDate 2021/6/23 20:30
 */
public class BloomFilterUtils<T> {
    private final int numHashFunctions;

    private final int bitSize;

    private final Funnel<T> funnel;


    public BloomFilterUtils(Funnel<T> funnel, int expectedInsertions, double fpp) {
        checkArgument(funnel != null, "funnel不能为空");
        checkArgument(
                expectedInsertions >= 0, "Expected insertions (%s) must be >= 0", expectedInsertions);
        checkArgument(fpp > 0.0, "False positive probability (%s) must be > 0.0", fpp);
        checkArgument(fpp < 1.0, "False positive probability (%s) must be < 1.0", fpp);
        this.funnel = funnel;
        // 计算bit数组长度
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        // 计算hash方法执行次数
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHash(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            offset[i - 1] = combinedHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            // 设定最小期望长度
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}

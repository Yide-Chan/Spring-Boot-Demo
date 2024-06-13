package com.chenyide.bloomfilter.filter;

import java.util.BitSet;
import java.util.Objects;
import java.util.Optional;

/**
 * @author chenyide
 * @version v1.0
 * @className JavaBloomFilter
 * @description 自定义一个布隆过滤器 （精简版）
 * @date 2024/4/24 14:05
 **/

public class JavaBloomFilter {

    /**
     * 一个长度为 10 亿的比特位
     */
    private static final int DEFAULT_SIZE = 256 << 22;

    /**
     * 不同哈希函数的种子，一般应取质数
     * 为了降低错误率，使用加法 hash 算法，所以定义一个8个元素的质数数组
     */
    private static final int[] SEEDS = {3, 5, 7, 11, 13, 31, 37, 61};

    /**
     * 相当于构建 8 个不同的 hash 算法 HashFunction 越多，误判率越低，也越慢
     */
    private static final HashFunction[] FUNCTIONS = new HashFunction[SEEDS.length];

    /**
     * 初始化布隆过滤器的 BitSet
     * BitSet 即“位图”，是一个很长的 “0/1”序列，他的功能就是存储0或者1
     */
    private static final BitSet BIT_SET = new BitSet(DEFAULT_SIZE);

    /**
     * 初始化多个包含 Hash 函数的类数组，每个类中的 Hash 函数都不一样
     */
    public JavaBloomFilter() {
        // 初始化多个不同的 Hash 函数
        for (int i = 0; i < SEEDS.length; i++) {
            FUNCTIONS[i] = new HashFunction(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    /**
     * 添加数据
     *
     * @param value 需要加入的值
     */
    public static void add(Object value) {
        if (value != null) {
            for (HashFunction f : FUNCTIONS) {
                //计算 hash 值并修改 bitmap 中相应位置为 true
                BIT_SET.set(f.hash(value), true);
            }
        }
    }

    /**
     * 判断相应元素是否存在
     *
     * @param value 需要判断的元素
     * @return 结果
     */
    public static boolean contains(Object value) {
        if (Objects.isNull(value)) {
            return false;
        }
        boolean ret = true;
        for (HashFunction f : FUNCTIONS) {
            // 一个 hash 函数返回 false 就说明这个数据不存在， 则跳出循环
            ret = ret && BIT_SET.get(f.hash(value));
//            ret = BIT_SET.get(f.hash(value));
//            一个 hash 函数返回 false 就说明这个数据不存在， 则跳出循环
//            if (!ret) {
//                break;
//            }
        }
        return ret;
    }


    /**
     * 用于计算 hash
     */
    static class HashFunction {
        /**
         * 数组长度 用于限制 hash 生成值的 最大值
         */
        private final int size;
        /**
         * 不同哈希函数的种子，一般应取质数
         */
        private final int seed;

        public HashFunction(int size, int seed) {
            this.size = size;
            this.seed = seed;
        }

        int hash(Object obj) {
            return (obj == null) ? 0 : Math.abs(seed * (size - 1) & (obj.hashCode() ^ (obj.hashCode() >>> 16)));
        }

    }
}

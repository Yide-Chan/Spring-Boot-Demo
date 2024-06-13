package com.chenyide.bloomfilter.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author chenyide
 * @version v1.0
 * @className GuavaBloomFilter Guava 他并没有做持久化。
 * @description 要引入依赖
 * <dependency>
 * <groupId>com.google.guava</groupId>
 * <artifactId>guava</artifactId>
 * <version>32.0.1-jre</version>
 * </dependency>
 * @date 2024/4/24 13:49
 **/

public class GuavaBloomFilter {
    public static void main(String[] args) {
        // 预期插入数量
        long capacity = 10000L;
        // 错误比率 误差率为1%
        double errorRate = 0.01;
        // 创建BloomFilter对象，需要传入Funnel对象，预估的元素个数，错误率
        BloomFilter<Long> filter = BloomFilter.create(Funnels.longFunnel(), capacity, errorRate);
        // BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 10000, 0.0001);
        // put值进去
        for (long i = 0; i < capacity; i++) {
            filter.put(i);
        }
        // 统计误判次数
        int count = 0;
        // 我在数据范围之外的数据，测试相同量的数据，判断错误率是不是符合我们当时设定的错误率
        for (long i = capacity; i < capacity * 2; i++) {
            if (filter.mightContain(i)) {
                count++;
            }
        }
        System.out.println(count);
    }
}


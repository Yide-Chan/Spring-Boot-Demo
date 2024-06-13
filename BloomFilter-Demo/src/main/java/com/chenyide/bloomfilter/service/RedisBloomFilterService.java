package com.chenyide.bloomfilter.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.BitSet;

/**
 * @author chenyide
 * @version v1.0
 * @className RedisBloomFilterService
 * @description
 * @date 2024/6/12 13:00
 **/

public class RedisBloomFilterService {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int EXPECTED_INSERTIONS = 1000;
    private static final double FALSE_POSITIVE_RATE = 0.01;
    private static final int BITSET_SIZE = (int) Math.ceil(-EXPECTED_INSERTIONS * Math.log(FALSE_POSITIVE_RATE) / (Math.log(2) * Math.log(2)));
    private static final JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT);
    public void RedisBloomFilter(String queryKey) {
        // 假设这是从数据库中查询出来的热点数据
        // String queryKey = "exampleKey";
        // 创建一个布隆过滤器
        BitSet bloomFilter = new BitSet(BITSET_SIZE);
        // 将所有可能的查询键值添加到布隆过滤器中
        bloomFilter.set(hash(queryKey));
        // 在查询缓存之前，先通过布隆过滤器判断查询键值是否存在
        if (!bloomFilter.get(hash(queryKey))) {
            System.out.println("Cache miss. Key does not exist in the bloom filter.");
            return;
        }
        // 进行缓存查询
        String result = getFromCache(queryKey);
        if (result != null) {
            System.out.println("Cache hit. Result: " + result);
            // TODO 返回缓存查询到的值
            return;
        }
        // 缓存中不存在该键值，执行其他查询逻辑，并将结果添加到缓存中
        result = doExpensiveDatabaseQuery(queryKey);
        addToCache(queryKey, result);
        System.out.println("Result: " + result);
        // TODO 返回数据库查询到的值
        return;
    }
    // 使用Redis获取缓存数据
    private static String getFromCache(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }
    // 向Redis中添加缓存数据
    private static void addToCache(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }
    // 执行昂贵的数据库查询
    private static String doExpensiveDatabaseQuery(String key) {
        // 执行查询逻辑并返回结果
        return "Result from database for key: " + key;
    }
    // 哈希函数，将查询键值映射到布隆过滤器的位集合中
    private static int hash(String key) {
        // 使用简单的哈希函数
        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash * 31 + key.charAt(i);
        }
        return Math.abs(hash % BITSET_SIZE);
    }
}

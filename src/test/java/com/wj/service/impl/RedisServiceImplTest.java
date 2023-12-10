package com.wj.service.impl;

import com.wj.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wj
 * @description
 */
@SpringBootTest
class RedisServiceImplTest {

    @Autowired
    RedisService redisService;

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void test(){
        System.out.println("11111");
        redisService.set("wj","wj111",10);
        System.out.println(redisService.get("wj"));
    }

    @Test
    void test2() throws IOException {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.1.10", 8001));
        jedisClusterNode.add(new HostAndPort("192.168.1.10", 8002));
        jedisClusterNode.add(new HostAndPort("192.168.1.10", 8003));
        jedisClusterNode.add(new HostAndPort("192.168.1.10", 8004));
        jedisClusterNode.add(new HostAndPort("192.168.1.10", 8005));
        jedisClusterNode.add(new HostAndPort("192.168.1.10", 8006));

        JedisCluster jedisCluster = null;
        try {
            //connectionTimeout：指的是连接一个url的连接等待时间
            //soTimeout：指的是连接上一个url，获取response的返回等待时间
            jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "wj", config);
            System.out.println(jedisCluster.set("cluster", "zhuge"));
            System.out.println(jedisCluster.get("cluster"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisCluster != null)
                jedisCluster.close();
        }
    }

}
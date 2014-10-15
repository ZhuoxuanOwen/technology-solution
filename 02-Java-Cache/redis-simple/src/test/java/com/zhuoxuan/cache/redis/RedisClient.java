package com.zhuoxuan.cache.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhuoxuan.cache.redis.context.RedisShardingClient;
import com.zhuoxuan.cache.redis.sharding.ShardingRedisClient;
import com.zhuoxuan.common.entity.UserDO;

public class RedisClient {

	@Test
	public void clientRedis() {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-common.xml");

		RedisShardingClient client = context.getBean(RedisShardingClient.class);

		// 向缓存中保存对象
		UserDO lisi = new UserDO();
		lisi.setUserId(3444);
		lisi.setSex(1);
		lisi.setUname("李四");
		lisi.setUnick("lisi");
		lisi.setEmail("lisi@126.com");
		// 调用方法处理
		boolean reusltCache = client.set("lisi", lisi);
		if (reusltCache) {
			System.out.println("向缓存中保存对象成功。");
		} else {
			System.out.println("向缓存中保存对象失败。");
		}

		lisi = client.get("lisi", UserDO.class);
		if (lisi != null) {
			System.out.println("从缓存中获取的对象，" + lisi.getUname() + "@" + lisi.getEmail());
		}

	}

}

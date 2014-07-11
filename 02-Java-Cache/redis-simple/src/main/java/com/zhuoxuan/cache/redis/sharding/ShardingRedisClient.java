package com.zhuoxuan.cache.redis.sharding;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * 
 * <p>
 * Sharding Redis Client 工具类
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月11日
 * @version： V1.0
 */
public class ShardingRedisClient {

	private static ShardedJedisPool shardedJedisPool;

	static {
		// 读取相关的配置
		ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
		int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
		int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
		int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));

		String ip = resourceBundle.getString("redis.ip");
		int port = Integer.parseInt(resourceBundle.getString("redis.port"));
		
		//设置配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWait);
		
		//设置分片元素信息
		JedisShardInfo shardInfo1 = new JedisShardInfo(ip,port);
		JedisShardInfo shardInfo2 = new JedisShardInfo(ip,port);
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		list.add(shardInfo1);
		list.add(shardInfo2);
		shardedJedisPool = new ShardedJedisPool(config, list);
	}

	
	/**
	 * 向缓存中设置字符串内容
	 * @param key key
	 * @param value value
	 * @return
	 * @throws Exception
	 */
	public static boolean  set(String key,String value) throws Exception{
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			jedis.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 向缓存中设置对象
	 * @param key 
	 * @param value
	 * @return
	 */
	public static boolean  set(String key,Object value){
		ShardedJedis jedis = null;
		try {
			String objectJson = JSON.toJSONString(value);
			jedis = shardedJedisPool.getResource();
			jedis.set(key, objectJson);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 删除缓存中得对象，根据key
	 * @param key
	 * @return
	 */
	public static boolean del(String key){
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 根据key 获取内容
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			Object value = jedis.get(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	

	/**
	 * 根据key 获取对象
	 * @param key
	 * @return
	 */
	public static <T> T get(String key,Class<T> clazz){
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			String value = jedis.get(key);
			return JSON.parseObject(value, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}
	
}

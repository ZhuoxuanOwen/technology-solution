package com.zhuoxuan.cache.redis.context;

import redis.clients.jedis.ShardedJedis;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>
 * Sharding 客户端操作类
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月11日
 * @version： V1.0
 */
public class RedisShardingClient extends RedisAbstractTemplate {

	/**
	 * 向缓存中设置字符串内容
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return
	 * @throws Exception
	 */
	public boolean set(String key, String value) throws Exception {
		try {
			ShardedJedis shardedJedis = atomRedisPool.getShardedJedis();
			shardedJedis.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			atomRedisPool.backtoPool();
		}
	}

	/**
	 * 向缓存中设置对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		try {
			ShardedJedis shardedJedis = atomRedisPool.getShardedJedis();

			String objectJson = JSON.toJSONString(value);
			shardedJedis.set(key, objectJson);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			atomRedisPool.backtoPool();
		}
	}

	/**
	 * 删除缓存中得对象，根据key
	 * 
	 * @param key
	 * @return
	 */
	public boolean del(String key) {
		try {

			ShardedJedis shardedJedis = atomRedisPool.getShardedJedis();
			shardedJedis.del(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			atomRedisPool.backtoPool();
		}
	}

	/**
	 * 根据key 获取内容
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		try {
			ShardedJedis shardedJedis = atomRedisPool.getShardedJedis();
			Object value = shardedJedis.get(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			atomRedisPool.backtoPool();
		}
	}

	/**
	 * 根据key 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		try {
			ShardedJedis shardedJedis = atomRedisPool.getShardedJedis();
			String value = shardedJedis.get(key);
			return JSON.parseObject(value, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			atomRedisPool.backtoPool();
		}
	}

}

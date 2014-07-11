package com.zhuoxuan.cache.redis.context;

import java.util.List;

import javax.annotation.PostConstruct;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * <p>
 * 	Redis缓存链接资源控制
 *  1、管理Redis资源链接
 *  2、进行主从选择策略，容灾切换选择
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月11日
 * @version： V1.0
 */
public class AtomRedisPool {
	
	private ShardedJedisPool shardedJedisPool; //基于分片的Redis资源池
	
	private JedisPoolConfig configPool; //资源池配置项
	
	private List<JedisShardInfo> sharded;//分片服务器信息配置
	
	//设置当前线程获取的ShardedJedis资源
	private ThreadLocal<ShardedJedis> shardedLocal = new ThreadLocal<ShardedJedis>(); 
	
	@PostConstruct
	public void refush(){
		
		shardedJedisPool = new ShardedJedisPool(configPool, sharded);
	}

	/**
	 * 获取链接操作资源
	 * @return
	 */
	public ShardedJedis getShardedJedis(){
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedLocal.set(shardedJedis);
		return shardedJedis;
	}
	
	/**
	 * 将链接资源回收
	 * @return
	 */
	public boolean backtoPool(){
		ShardedJedis shardedJedis = shardedLocal.get();
		shardedJedisPool.returnResource(shardedJedis);
		return true;
	}
	
	public JedisPoolConfig getConfigPool() {
		return configPool;
	}

	public void setConfigPool(JedisPoolConfig configPool) {
		this.configPool = configPool;
	}

	public List<JedisShardInfo> getSharded() {
		return sharded;
	}

	public void setSharded(List<JedisShardInfo> sharded) {
		this.sharded = sharded;
	}
	
	
	
	
}

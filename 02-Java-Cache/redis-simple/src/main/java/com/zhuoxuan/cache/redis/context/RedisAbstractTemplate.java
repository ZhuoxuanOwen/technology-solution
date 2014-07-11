package com.zhuoxuan.cache.redis.context;

/**
 * 
 * <p>
 *  Redis 公共操作模板
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月11日
 * @version： V1.0
 */
public abstract class RedisAbstractTemplate {

	
	protected AtomRedisPool atomRedisPool; //控制资源的上下文对象

	public AtomRedisPool getAtomRedisPool() {
		return atomRedisPool;
	}

	public void setAtomRedisPool(AtomRedisPool atomRedisPool) {
		this.atomRedisPool = atomRedisPool;
	}

	
	
	
	
	
	
}

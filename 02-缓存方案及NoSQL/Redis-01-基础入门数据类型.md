
## 一、常用数据类型

#### 字符串类型

	  set key=value , get key  output value  (key值不能太长，简单就好；value 不要大于1G长度)
	  
	  
	  ① 业务场景作为计数器使用：
	  	 set counter = 100;
	  	 incr counter => 101;
	  	 incr counter by 10 => 111;  反之 decr 同样；
	  	 
	  	 注意：incr 会将字符类型的数字转换成数字型的处理的。
	   
	   
#### 列表类型

	Redis lists基于Linked Lists实现。这意味着即使在一个list中有数百万个元素，在头部或尾部添加一个元素的操作，其时间复杂度也是常数级别的。用LPUSH 命令在十个元素的list头部添加新元素，和在千万元素list头部添加新元素的速度相同。
	
	Redis Lists用linked list实现的原因是：对于数据库系统来说，至关重要的特性是：能非常快的在很大的列表上添加元素。另一个重要因素是，正如你将要看到的：Redis lists能在常数时间取得常数长度。
	
	lpush , rpush => rpush messages “hello???” , 在list前后插入内容。
	
	lrange  获取list中得内容 lrange 0 10 => 从0开始获取10个记录。
	
	在业务场景下，压入list的时对象的引用id，而不是内容。
	
	
#### 集合（Sets）类型

	Redis能够将一系列不重复的值存储成一个集合。
	
	sadd things todo1;
	sadd things todo2;
	sadd things todo3;
	
	smembers things; output 集合内的元素，不过是没有顺序的。
	
	也有其他的api对集合操作，具体详见命令。
	

## 二、Publish/Subscribe


#### 订阅信息管道

	subscribe todotask;
	
	pushlish todotask todeluser1;
	pushlish todotask todeluser2;
	. . .
	
	实现了阻塞式的消费者模式，将需要处理的任务封装起来，形成task; 订阅对应的task进行任务处理；
	
	广播事件模式，例如商品中心商品下架，通过这样发送消息给关注下架商品的业务系统。
	
	注意：可以为多个 subscribe 客户端。


#### 按照一定模式批量订阅

	psubscribe todo*   实现批量订阅
	
	

## 三 数据过期设置

	ttl 获取对应key的过期时间 ， -1 为永久 。
	
	expire key second   设置失效时间。

	
	
## 四 事务性

	redis 是通过控制命令批次执行来控制事务性的。
	
	multi  开始事务； 
	
	discard 取消执行；
	
	exec  执行；
	
	
## 五 管理命令

	info => redis等状态的描述.
	
	dbsize => 数据库持久化的容量.
	
	flushdb => 清除持久化的数据.



## 六 数据持久化

#### 数据快照

	数据快照是不定时的遍历内存中所有的数据文件，存储到硬盘上的一个 rdb的数据文件中；这个操作是使用save命令完成的;需要在redis.conf 文件完成如下配置进行支持的。
	
	save 900 1
	save 300 10
	
	900秒内有一个key发生变化，就save
	300秒内有10个key发生变化就save
	
	





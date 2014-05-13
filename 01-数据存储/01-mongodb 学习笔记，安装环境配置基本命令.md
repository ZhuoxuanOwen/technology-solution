####一、MongoDB安装配置
 
######1、获取最新版本：
 
 	
 	wget http://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.0.2.tgz
 	
######2、解压缩即可执行
 
    tar zxvf mongodb-linux-x86_64-2.0.2.tgz
	tar zxvf mongodb-linux-x86_64-2.0.2.tgz
    cd /usr/mongodb-linux-x86_64-2.0.2/bin
    但是在运行前，需要创建mongodb需要的存放数据和日志的目录：
    sudo mkdir -p /data/db/journal
    sudo chmod -R 777 /data/db/
    启动mongodb server,-journal 代表要写日志，-maxConns=2400代表mongodb 可以接受2400个   tcp连接，-rest代表可以允许客户端通过rest API访问mongdb server.
    
    ./mongod -journal -maxConns=2400 -rest
    
######3、相关说明
 
    服务程序启动后,终端会显示一些信息，比如：
    Wed Aug 31 16:40:03 [initandlisten] MongoDB starting : pid=2410 port=27017 dbpath=/data/db/ 64-bit
    Wed Aug 31 16:40:03 [initandlisten] db version v2.0.2, pdfile version 4.5
    Wed Aug 31 16:40:03 [initandlisten] git version: c206d77e94bc3b65c76681df5a6b605f68a2de05
    Wed Aug 31 16:40:03 [initandlisten] build sys info: Linux bs-linux64.10gen.cc 2.6.21.7-2.ec2.v1.2.fc8xen #1 SMP Fri Nov 20 17:48:28 EST 2009 x86_64 BOOST_LIB_VERSION=1_41
    Wed Aug 31 16:40:03 [initandlisten] journal dir=/data/db/journal
    Wed Aug 31 16:40:03 [initandlisten] recover : no journal files present, no recovery needed
    Wed Aug 31 16:40:06 [initandlisten] preallocateIsFaster=true 33.84
    Wed Aug 31 16:40:08 [initandlisten] preallocateIsFaster=true 36.84
    Wed Aug 31 16:40:11 [initandlisten] preallocateIsFaster=true 37.48
    Wed Aug 31 16:40:11 [initandlisten] preallocating a journal file /data/db/journal/prealloc.0
    Wed Aug 31 16:41:03 [initandlisten] preallocating a journal file /data/db/journal/prealloc.1
	Wed Aug 31 16:41:55 [initandlisten] preallocating a journal file /data/db/journal/prealloc.2
	Wed Aug 31 16:42:48 [initandlisten] waiting for connections on port 27017
    Wed Aug 31 16:42:48 [initandlisten] —maxConns too high, can only handle 819
    Wed Aug 31 16:42:48 [websvr] web admin interface listening on port 28017

######4、环境信息
	
	机器IP: 10.0.14.218
    安装目录 : /usr/local/mongodb/bin
    数据存储目录: /data/db
    web console : http://10.0.14.218:28017/
    web admin port : 28017

####二、mongodb入门基础命令
* show dbs:显示数据库列表 
* show collections：显示当前数据库中的集合（类似关系数据库中的表）
* show users：显示用户
* db.help()：显示数据库操作命令，里面有很多的命令 
* db.foo.find()：对于当前数据库中的foo集合进行数据查找（由于没有条件，会列出所有数据）

---

* use yourDB;  切换创建数据库，当创建一个集合(table)的时候会自动创建当前数据库。
* db.dropDatabase(); 删除当前的数据库，请谨慎使用。
* db.cloneDatabase(“127.0.0.1”); 将指定机器上的数据库的数据克隆到当前数据库
* db.copyDatabase("mydb", "temp", "127.0.0.1");将本机的mydb的数据复制到temp数据库中
* db.repairDatabase(); 修复当前数据库
* db.getName(); 获取当前数据库名称，和  db 命令效果一样的。
* db.stats(); 查看db的状态。
* db.version(); 查看db 当前的版本。
* db.getMondo(); 查看当前mongodb的链接服务器地址。

---

* db.createCollection(“collName”, {size: 20, capped: 5, max: 100});
     创建一个聚集集合。

* db.getCollection('dict'); 得到指定名称的聚集集合。
* db.getCollectionNames(); 获取当前db下所有聚集集合的名称。
* db.printCollectionStats(); 显示当前db所有聚集索引的状态。
* db.dict.help(); 查看帮助

---

*  db.addUser("zhuoxuan", "admin", true); 添加用户、设置密码、是否只读
*  db.auth('zhuoxuan','admin'); 数据库认证，安全模式
*  show users; 显示所有的用户。
*  db.removeUser("zhuoxuan"); 删除用户账号



















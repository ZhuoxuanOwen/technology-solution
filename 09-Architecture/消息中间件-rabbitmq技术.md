## 消息中间件-rabbitmq技术

### 1、环境准备，Erlang安装

	su -c 'rpm -Uvh http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm'  
	
	su -c 'yum install foo'  
	
	wget -O /etc/yum.repos.d/epel-erlang.repo http://repos.fedorapeople.org/repos/peter/erlang/epel-erlang.repo  
	
	yum install erlang  
	
### 2、安装rabbitmq

	wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.1.1/rabbitmq-server-3.1.1.tar.gz
	
	tar -zxf rabbitmq-server-3.1.1.tar.gz
	
	cd rabbitmq-server-3.1.1.tar.gz
	
	
	设置环境变量
	export TARGET_DIR=/usr/local/rabbitmq
    export SBIN_DIR=/usr/local/rabbitmq/sbin
	export MAN_DIR=/usr/local/rabbitmq/man
	export DOC_INSTALL_DIR=/usr/local/rabbitmq/doc
	
	
	make
	
	make install TARGET_DIR=/opt/mq/rabbitmq SBIN_DIR=/opt/mq/rabbitmq/sbin MAN_DIR=/opt/mq/rabbitmq/man
	

### 3、安装web插件管理界面

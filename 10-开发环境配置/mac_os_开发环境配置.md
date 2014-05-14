#### mac os 开发环境配置

##### 一、基础工具安装

###### 1、安装 wget
	
	curl -O http://ftp.gnu.org/gnu/wget/wget-1.13.4.tar.gz
	tar -xzf wget-1.13.4.tar.gz
	cd wget-1.13.4
	./configure --with-ssl=openssl
	make
	sudo make install
	wget --help
	cd .. && rm -rf wget*
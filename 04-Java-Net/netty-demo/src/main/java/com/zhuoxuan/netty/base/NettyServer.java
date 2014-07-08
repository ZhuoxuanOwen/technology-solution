package com.zhuoxuan.netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.junit.Test;

/**
 * 
 * <p>
 * 	Netty Server Simple
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */

public class NettyServer {
	
	private final int port = 8989;
	
	@Test
	public void nettyServer(){
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup,workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChildChannelHandler());
			
			//绑定端口、同步等待
			ChannelFuture futrue = serverBootstrap.bind(port).sync();
			
			//等待服务监听端口关闭
			futrue.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//退出，释放线程等相关资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

		
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {

			ch.pipeline().addLast(new SimpleServerHandler());
		}
	}
	
}

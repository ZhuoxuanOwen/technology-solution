package com.zhuoxuan.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;

import org.junit.Test;


/**
 * 
 * <p>
 * 	Netty Server Simple
 * </p>
 * 
 * LineBasedFrameDecoder + 消息中得换行符
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
			//ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//			
//			ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//			ch.pipeline().addLast(new DelimiterBasedFrameDecoder(2048, delimiter));
//			
			//ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
			ch.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
			ch.pipeline().addLast(new ObjectEncoder());
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new UserRespServerHandler());
		}
	}
	
	
}

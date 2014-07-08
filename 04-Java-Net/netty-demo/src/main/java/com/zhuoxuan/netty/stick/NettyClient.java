package com.zhuoxuan.netty.stick;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import org.junit.Test;


/**
 * 
 * <p>
 * 	NettyClient  实现
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class NettyClient {

	
	
	public void connect(int port,String host){
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					
//					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(2048, delimiter));
//					
					ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new StickClientHandler());
				}
			});
			//发起异步链接操作
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭，释放线程资源
			group.shutdownGracefully();
		}
	}
	
	@Test
	public void nettyClient(){
		
		new NettyClient().connect(8989, "localhost");
	}
	
}

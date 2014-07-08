package com.zhuoxuan.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * 
 * <p>
 * 	Server接收消息处理Handler
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf buf = (ByteBuf)msg;
		byte [] req = new byte[buf.readableBytes()];
		
		buf.readBytes(req);
		
		String message = new String(req,"UTF-8");
		
		System.out.println("Netty-Server:Receive Message,"+ message);
	
	}
	
	
	

}

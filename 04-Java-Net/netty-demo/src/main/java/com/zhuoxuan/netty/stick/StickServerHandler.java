package com.zhuoxuan.netty.stick;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
public class StickServerHandler extends ChannelInboundHandlerAdapter {

	private int countor = 0;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		String message = (String)msg;
		
		System.out.println("Netty-Server:Receive Message,"+ message+",The current counter is :" + countor++);
		//返回
		//String currentTime = String.valueOf(System.currentTimeMillis())+System.getProperty("line.separator");
		
		String currentTime = String.valueOf(System.currentTimeMillis())+"$_";
		
		
		String replyMessage = "Server Reply,"+message+",当前时间："+currentTime;
		ByteBuf resp = Unpooled.copiedBuffer(replyMessage.getBytes());
		ctx.writeAndFlush(resp);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		System.out.println("Server has Exception,"+ cause.getCause());
	
	}
	
	
	

}

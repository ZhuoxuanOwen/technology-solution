package com.zhuoxuan.netty.stick;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * <p>
 * Client Handler
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class StickClientHandler extends ChannelInboundHandlerAdapter {
	
	
	private int counter = 0;

	public StickClientHandler() {
		
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		for (int i = 0; i < 100; i++) {
			//byte [] req = ("Call-User-Service"+System.getProperty("line.separator")).getBytes();
			byte [] req = ("Call-User-Service"+"$_").getBytes();
			ByteBuf clientMessage = Unpooled.buffer(req.length);
			clientMessage.writeBytes(req);
			
			ctx.writeAndFlush(clientMessage);
		}
		
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		String message = String.valueOf(msg);
		
		System.out.println("Netty-Client:Receive Message,"+ message+",The counter is :"+ counter++);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		System.out.println("Client has Exception,"+ cause.getCause());
	}
	

}

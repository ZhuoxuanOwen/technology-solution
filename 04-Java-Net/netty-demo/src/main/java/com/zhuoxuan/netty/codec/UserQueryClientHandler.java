package com.zhuoxuan.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.zhuoxuan.net.common.UserQuery;

/**
 * 
 * <p>
 * 用户查询请求 Handler
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class UserQueryClientHandler extends ChannelInboundHandlerAdapter {
	
	

	public UserQueryClientHandler() {
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		for (int i = 0; i < 100; i++) {
			UserQuery userQuery = new UserQuery();
			userQuery.setUserId(1001+i);
			ctx.write(userQuery);
		}
		ctx.flush();
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		String message = String.valueOf(msg);
		
		System.out.println("Netty-Client:Receive Message,"+ message);
	}
	
	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		System.out.println("Client has Exception,"+ cause.getCause());
	}
	

}

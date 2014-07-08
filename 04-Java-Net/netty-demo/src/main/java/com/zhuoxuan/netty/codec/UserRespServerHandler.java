package com.zhuoxuan.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.zhuoxuan.net.common.UserDO;
import com.zhuoxuan.net.common.UserQuery;
/**
 * 
 * <p>
 *  用户查询返回
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class UserRespServerHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		UserQuery userQuery = (UserQuery) msg;
		
		System.out.println("收到来自客户端的查询请求:"+ String.valueOf(userQuery));
		
		if(userQuery != null && userQuery.getUserId()!= 0){
			UserDO userDO = getUserById(userQuery.getUserId());
			ctx.writeAndFlush(userDO);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		System.out.println("Server has Exception,"+ cause.getCause());
	
	}
	
	
	private UserDO getUserById(int userId){
		
		if(userId % 2 == 0){
			UserDO zhuoxuan = new UserDO();
			zhuoxuan.setUserId(userId);
			zhuoxuan.setSex(1);
			zhuoxuan.setUname("卓轩");
			zhuoxuan.setUnick("zhuoxuan");
			zhuoxuan.setEmail("zhuoxuan@mogujie.com");
			return zhuoxuan;
		}else{
			UserDO zhuoxuan = new UserDO();
			zhuoxuan.setUserId(userId);
			zhuoxuan.setSex(1);
			zhuoxuan.setUname("张三");
			zhuoxuan.setUnick("zhangsan");
			zhuoxuan.setEmail("zhuoxuan@mogujie.com");
			return zhuoxuan;
		}
		
	}
	

}

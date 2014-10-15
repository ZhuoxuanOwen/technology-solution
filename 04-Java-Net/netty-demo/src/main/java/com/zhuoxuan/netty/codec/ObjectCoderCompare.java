package com.zhuoxuan.netty.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.zhuoxuan.net.common.UserDO;

/**
 * 
 * <p>
 * 	对比对象编解码 优劣
 * 
 *  代码通过从同一个对象，使用jdk序列化 和 二进制编解码 ，从执行时间，产生的字节大小作对比
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月8日
 * @version： V1.0
 */
public class ObjectCoderCompare {

	/**
	 * 对比序列化文件大小
	 */
	@Test
	public void sizeByteCompare(){
		
		try {
			UserDO zhuoxuan = new UserDO();
			zhuoxuan.setUserId(113445);
			zhuoxuan.setSex(1);
			zhuoxuan.setUname("卓轩");
			zhuoxuan.setUnick("test");
			zhuoxuan.setEmail("126@126.com");
			
			System.out.println("byte array 序列化大小:" + zhuoxuan.coder().length);
			
			//jdk 序列化对象
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
			objectOutputStream.writeObject(zhuoxuan);
			objectOutputStream.flush();
			objectOutputStream.close();
			byte[] byteArray = bos.toByteArray();
			System.out.println("jdk序列化对象大小：" + byteArray.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行时间，性能对比
	 */
	@Test
	public void performCompare(){
		
		try {
			UserDO zhuoxuan = new UserDO();
			zhuoxuan.setUserId(113445);
			zhuoxuan.setSex(1);
			zhuoxuan.setUname("卓轩");
			zhuoxuan.setUnick("zhuoxuan");
			zhuoxuan.setEmail("test@126.com");
			
			long startTime = System.currentTimeMillis();
			int count = 1000000;
			for (int i = 0; i < count; i++) {
				zhuoxuan.coder();
			}
			long endTime = System.currentTimeMillis();
			System.out.println("byte array 执行时间：" + (endTime-startTime) + "ms");
			
			startTime = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				//jdk 序列化对象
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
				objectOutputStream.writeObject(zhuoxuan);
				objectOutputStream.flush();
				objectOutputStream.close();
				byte[] byteArray = bos.toByteArray();
			}
			endTime = System.currentTimeMillis();
			System.out.println("jdk 序列化对象 执行时间：" + (endTime-startTime) + "ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

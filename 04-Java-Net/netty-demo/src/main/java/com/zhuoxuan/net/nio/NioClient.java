package com.zhuoxuan.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import sun.security.ntlm.Client;

/**
 * 
 * <p>
 *   nio client
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class NioClient {

	private static final int BLOCK_SIZE = 4096;
	
	private static ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK_SIZE);
	
	private static ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK_SIZE);
	
	private static final  InetSocketAddress SERVER_ADDRESS = new InetSocketAddress("127.0.0.1",8787);
	
	
	public static void main(String[] args) {
		
		try {
			//打开socket通道
			SocketChannel socketChannel = SocketChannel.open();
			//设置为非阻塞模式
			socketChannel.configureBlocking(false);
			//打开选择器
			Selector selector = Selector.open();
			//向selector 选择器注册此通道
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			//链接
			socketChannel.connect(SERVER_ADDRESS);
			
			SocketChannel client;
			while (true) {
				//选择一组键
				selector.select();
				//返回此选择器的已选择键集
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				//遍历对应的 SelectionKey 处理
				while (iterator.hasNext()) {
					SelectionKey selectionKey = (SelectionKey) iterator.next();
					//判断此键的通道是否已完成其套接字连接操作
					if (selectionKey.isConnectable()) {
						System.out.println("Client:  already connected.");
						client = (SocketChannel)selectionKey.channel();
						//判断该通道是否进行连接过程、完成连接过程
						if(client.isConnectionPending()){
							client.finishConnect();
							
							sendBuffer.clear();
							sendBuffer.put("hello nio server".getBytes());
							sendBuffer.flip();
							
							client.write(sendBuffer); //将数据写入该通道
							client.register(selector, SelectionKey.OP_READ);
						}
					}
					else if(selectionKey.isReadable()){
						//获取该键中对应的通道
						client = (SocketChannel)selectionKey.channel();
						
						receiveBuffer.clear();
						int count = client.read(receiveBuffer);
						if(count > 0){
							String receiveMessage = new String(receiveBuffer.array(),0,count);
							System.out.println("Client：接收到来自Server的消息，" + receiveMessage);
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
					else if(selectionKey.isWritable()){
						sendBuffer.clear();  
	                    client = (SocketChannel) selectionKey.channel();  
	                    String sendText = "hello server,key..";
	                    sendBuffer.put(sendText.getBytes());  
	                     //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
	                    sendBuffer.flip();  
	                    client.write(sendBuffer);  
	                    System.out.println("Client:客户端向服务器端发送数据--："+sendText);  
	                    client.register(selector, SelectionKey.OP_READ);
						
					}
				}
				selectionKeys.clear();
				Thread.sleep(3000);
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
}

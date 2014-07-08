package com.zhuoxuan.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * 
 * <p>
 * 	基于nio的Server
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月7日
 * @version： V1.0
 */
public class NioServer {
	
	private final int port = 8787;
	private final int BLOCK_SIZE = 4096;
	
	private Selector selector;
	
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK_SIZE);
	private ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK_SIZE);
	
	
	

	//构造函数
	public NioServer() throws IOException {
		//打开服务器套接字通道 
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//服务器配置为非阻塞模式
		serverSocketChannel.configureBlocking(false);
		//获取与通道关联的 ServerSocket对象
		ServerSocket serverSocket = serverSocketChannel.socket();
		//绑定端口
		serverSocket.bind(new InetSocketAddress(port));
		//打开一个选择器
		selector = Selector.open();
		//注册到selector上，等待连接
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		System.out.println("Server:init successfuly.");
	}
	
	/**
	 * 监听端口
	 */
	private void linstenr() throws Exception{
		
		while (true) {
			//选择一组键
			selector.select();
			//返回获取选择的键集
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			if(selectionKeys.isEmpty()){
				continue;
			}
			//遍历，循环处理请求的键集
			Iterator<SelectionKey> iterator =  selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = (SelectionKey) iterator.next();
				iterator.remove();
				handlerKey(selectionKey);
			}
			
			Thread.sleep(4000);
		}
		
	}
	
	/**
	 * 处理对应的  SelectionKey
	 * @param selectionKey
	 */
	private void handlerKey(SelectionKey selectionKey) throws IOException{
		
		ServerSocketChannel server;
		SocketChannel client;
		
		// 测试此键的通道是否已准备好接受新的套接字连接
		if(selectionKey.isAcceptable()){
			//此键对应的关联通道
			server = (ServerSocketChannel)selectionKey.channel();
			//接受到此通道套接字的连接
			client = server.accept();
			//配置为非阻塞
			client.configureBlocking(false);
			//注册到selector 等待连接
			client.register(selector, SelectionKey.OP_READ);
			
		}
		
		else if (selectionKey.isReadable()) {
		
			client = (SocketChannel)selectionKey.channel();
			//将缓冲区清空，下面读取
			receiveBuffer.clear();
			//将客户端发送来的数据读取到 buffer中
			int count = client.read(receiveBuffer);
			if(count >0){
				String receiveMessage = new String(receiveBuffer.array(),0,count);
				System.out.println("Server:接受客户端的数据:" + receiveMessage);
				client.register(selector, SelectionKey.OP_WRITE);
			}
		}
		
		else if (selectionKey.isWritable()) {
			//发送消息buffer 清空
			sendBuffer.clear();
			//返回该键对应的通道
			client = (SocketChannel)selectionKey.channel();
			String sendMessage = "Send form Server...Hello... "+new Random().nextInt(100)+" .";
			//向缓冲区中写入数据
			sendBuffer.put(sendMessage.getBytes());
			//put了数据，标志位被改变
			sendBuffer.flip();
			//数据输出到通道
			client.write(sendBuffer);
			System.out.println("Server:服务器向客户端发送数据:" + sendMessage);
			client.register(selector, SelectionKey.OP_READ);
		}
		
		
	}
	
	
	public static void main(String[] args) {
		
		try {
			
			NioServer nioServer = new NioServer();
			nioServer.linstenr();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}

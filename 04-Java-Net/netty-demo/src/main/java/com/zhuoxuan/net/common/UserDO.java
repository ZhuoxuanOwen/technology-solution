package com.zhuoxuan.net.common;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 *
 * <p>
 *用户实体对象定义
 * </p>
 *
 * @author 卓轩
 * @创建时间：2014年6月20日
 * @产品: UIC
 * @version： V1.0
 */
public class UserDO implements Serializable {

	private static final long serialVersionUID = 7289036533757178921L;



	/**
	 * userid
	*/
	private int userId;

	/**
	 * email
	*/
	private String email;

	/**
	 * uname
	*/
	private String uname;

	/**
	 * unick
	*/
	private String unick;

	/**
	 * sex
	*/
	private Integer sex;




	public int getUserId() {
		return userId;
	}




	public void setUserId(int userId) {
		this.userId = userId;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getUname() {
		return uname;
	}




	public void setUname(String uname) {
		this.uname = uname;
	}




	public String getUnick() {
		return unick;
	}




	public void setUnick(String unick) {
		this.unick = unick;
	}




	public Integer getSex() {
		return sex;
	}




	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "UserDO ["+this.userId+"],["+this.uname+"],["+this.unick+"],["+this.email+"]";
	}


	public byte [] coder(){

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		byte [] email = this.email.getBytes();
		buffer.putInt(email.length);
		buffer.put(email);
		
		byte [] uname =this.uname.getBytes();
		buffer.putInt(uname.length);
		buffer.put(uname);
		
		byte [] unick = this.unick.getBytes();
		buffer.putInt(unick.length);
		buffer.put(unick);
		
		buffer.putInt(this.sex);
		buffer.putInt(this.userId);
		
		buffer.flip();
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}

}

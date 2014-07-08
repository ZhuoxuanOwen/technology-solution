package com.zhuoxuan.net.common;

import java.io.Serializable;

/**
 * 
 * <p>
 * 	用户查询对象
 * </p>
 * 
 * @author 卓轩
 * @创建时间：2014年7月8日
 * @version： V1.0
 */
public class UserQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8228194670330971606L;
	
	private int userId;
	private String uname;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	
	@Override
	public String toString() {
		return "UserQuery,["+this.userId+"],["+this.uname+"]";
	}

}

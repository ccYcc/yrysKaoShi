package com.ccc.test.pojo;

import java.io.Serializable;

import com.ccc.test.utils.StringUtil;

/**封装简单的信息类
 * @author Trible Chen
 *
 */
public class MsgInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public MsgInfo(){
		
	}
	public MsgInfo(int code , String msg){
		this.code = code;
		this.message = msg;
	}
	
	int code;
	String message;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setMsg(int code,String msg){
		this.code = code;
		this.message = msg;
	}
	@Override
	public String toString() {
		String msg = getMessage();
		if ( !StringUtil.isEmpty(msg) ){
			msg = msg.replace("\n", ";");
		}
		return msg+"("+getCode()+")";
	}
	
}

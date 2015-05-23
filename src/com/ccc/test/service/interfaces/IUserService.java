package com.ccc.test.service.interfaces;

import java.io.Serializable;

import com.ccc.test.pojo.UserInfo;

public interface IUserService {

	/**登录接口
	 * @return 返回token字符串，用于该用户的其他操作 可以简单返回id  "-1"代表用户为空
	 */
	String login(String username,String password,String type)  throws Exception ;

	/**登出接口
	 * @param token 用户唯一表示token
	 * @return 成功返回0
	 * @throws Exception
	 */
	Serializable loginOut(String token)  throws Exception ;
	
	/**注册接口
	 * @return 返回信息MsgInfo
	 */
	Serializable register(String username,String password,String conPassword, String type)  throws Exception ;
	
	/**更新接口
	 * @return 更新成功返回新的用户对象
	 */
	Serializable updateUserInfo(UserInfo info)  throws Exception ;
	
	/**根据需要获取用户部分信息，不包括密码等敏感信息
	 * @param token 用户的token字符串
	 * @return 返回用户数据对象
	 */
	Serializable fetchUserInfo(String token)  throws Exception;
	
}

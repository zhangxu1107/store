package cn.zx.store.service;

import java.sql.SQLException;

import cn.zx.store.domain.User;

public interface UserService {

	//用户注册
	void userRegist(User user) throws SQLException;

	//用户激活
	boolean userActive(String code) throws SQLException;

	//用户登录
	User userLogin(User user)  throws SQLException ;

}

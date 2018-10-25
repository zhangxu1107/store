package cn.zx.store.dao;

import java.sql.SQLException;

import cn.zx.store.domain.User;

public interface UserDao {

	//用户注册
	void userRegist(User user) throws SQLException;

	//用户激活
	User userActive(String code) throws SQLException;

	//更新用户
	void updateUser(User user) throws SQLException;

	//用户登录
	User userLogin(User user) throws SQLException;


}

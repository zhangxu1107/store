package cn.zx.store.dao.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.zx.store.dao.UserDao;
import cn.zx.store.domain.User;
import cn.zx.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	// 实现用户注册
	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		qr.update(sql, params);
	}

	// 实现用户激活
	@Override
	public User userActive(String code) throws SQLException{
		String sql = "SELECT * FROM user WHERE code = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	// 实现用户更新
	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		Object[] params={user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,params);
	}

	// 实现用户登录
	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
	}

}

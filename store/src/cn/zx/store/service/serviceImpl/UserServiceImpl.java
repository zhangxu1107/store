package cn.zx.store.service.serviceImpl;

import java.sql.SQLException;

import cn.zx.store.dao.UserDao;
import cn.zx.store.dao.daoImpl.UserDaoImpl;
import cn.zx.store.domain.User;
import cn.zx.store.service.UserService;
import cn.zx.store.utils.BeanFactory;
import cn.zx.store.utils.MailUtils;

public class UserServiceImpl implements UserService {

	UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");

	// 实现用户注册
	@Override
	public void userRegist(User user) throws SQLException {
		// 实现注册功能
		userDao.userRegist(user);
		try {
			// 向用户邮箱发送一份激活邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 实现用户激活
	@Override
	public boolean userActive(String code) throws SQLException {

		// 调用业务层功能:根据激活码查询用户 select * from user where code=?
		User user = userDao.userActive(code);
		// 如果用户不为空,激活码正确的,可以激活
		if (null != user) {
			// 可以根据激活码查询到一个用户
			// 可以修改用户的状态，清除激活码
			user.setState(1);
			user.setCode(null);
			// 对数据库执行一次真实的更新操作 update user set state=1,code=null where uid = ?
			// 修改通用语句 update user set
			// username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,status=?,code=?
			// where uiud=?
			userDao.updateUser(user);
			return true;
		} else {
			// 不可以根据激活码查询到用户
			return false;
		}

	}

	// 实现用户登录
	@Override
	public User userLogin(User user)  throws SQLException {
		// select * from user where username=? and password = ?
		User uu = userDao.userLogin(user);
		if (null == uu) {
			throw new RuntimeException("用户名和密码不匹配!");
		} else if (uu.getState() == 0) {
			throw new RuntimeException("用户未激活！");
		} else {
			return uu;
		}
		
	}

}

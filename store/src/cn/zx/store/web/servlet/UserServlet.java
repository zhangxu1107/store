package cn.zx.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.store.domain.User;
import cn.zx.store.service.UserService;
import cn.zx.store.service.serviceImpl.UserServiceImpl;
import cn.zx.store.utils.CookUtils;
import cn.zx.store.utils.MailUtils;
import cn.zx.store.utils.MyBeanUtils;
import cn.zx.store.utils.UUIDUtils;
import cn.zx.store.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// 跳转到用户注册页面
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	// 跳转到用户登录页面
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}

	// userRegist 用户注册
	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);

		// 为用户的其他属性赋值
		user.setUid(UUIDUtils.getId()); // 用户id
		user.setState(0); // 状态值
		user.setCode(UUIDUtils.getCode()); // 激活码

		System.out.println(user);

		// 调用业务层注册功能
		UserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user);

			// 注册成功,向用户邮箱发送信息,跳转到提示页面
			// 发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活！");
			return "/jsp/info.jsp";
		} catch (Exception e) {
			// 注册失败,跳转到提示页面
			request.setAttribute("msg", "用户注册失败，请重试！");
		}

		return "/jsp/info.jsp";
	}

	// active 用户激活
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 服务端获取到激活码,和数据库中已经存在的激活码对比,如果存在,激活成功,更改用户激活状态1,转发到登录页面,提示:激活成功,请登录.
		String code = request.getParameter("code");

		// 调用业务层功能:根据激活码查询用户 select * from user where code=?
		UserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		if (flag == true) {
			// 用户激活成功，向requet放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "/jsp/login.jsp";
		} else {
			// 用户激活失败，向requet放入提示信息，转发到提示页面
			request.setAttribute("msg", "用户激活失败，请重试或联系客服！");
			return "/jsp/info.jsp";
		}
	}

	// userLogin 用户登录
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取用户名和密码
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		// 调用业务层登录功能
		UserService userService = new UserServiceImpl();
		User user2 = null;
		try {
			//user2 = select * fron user where username=? and password =?
			//用户登录成功，将用户信息放入session中
			user2 = userService.userLogin(user);
			request.getSession().setAttribute("loginUser", user2);
			
			//在登录成功的基础上,判断用户是否选中自动登录复选框
			String autoLogin=request.getParameter("autoLogin");
			if("yes".equals(autoLogin)){
				//用户选中自动登录复选框
				Cookie ck=new Cookie("autoLogin",user2.getUsername()+"#"+user2.getPassword());
				ck.setPath("/store");
				ck.setMaxAge(60*60*24*7);
				response.addCookie(ck);
			}
			//remUser
			String remUser=request.getParameter("remUser");
			if("yes".equals(remUser)){
				//用户选中自动登录复选框
				Cookie ck=new Cookie("remUser",user2.getUsername());
				ck.setPath("/store");
				ck.setMaxAge(60*60*24*7);
				response.addCookie(ck);
			}
			response.sendRedirect("/store/index.jsp");
			
			return null;
		} catch(Exception e) {
			//用户登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			//向request放入失败信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
		
	}
	
	
	// logOut 用户退出登录
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//清除Session
		request.getSession().invalidate();
		
		Cookie ck=CookUtils.getCookieByName("autoLogin", request.getCookies());
		if(null!=ck){
			ck.setMaxAge(0);
			ck.setPath("/store");
			response.addCookie(ck);
		}
		
		//重新定向到首页
		response.sendRedirect("/store/index.jsp");
		
		return null;
	}
}

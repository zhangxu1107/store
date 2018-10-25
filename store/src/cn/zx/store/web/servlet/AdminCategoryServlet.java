package cn.zx.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.store.domain.Category;
import cn.zx.store.service.CategoryService;
import cn.zx.store.service.serviceImpl.CategoryServiceImpl;
import cn.zx.store.utils.UUIDUtils;
import cn.zx.store.web.base.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findAllCats
	public String findAllCates(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取全部分类信息
		CategoryService CategoryService = new CategoryServiceImpl();
		List<Category> list = CategoryService.getAllCates();
		// 全部分类信息放入request
		req.setAttribute("allCates", list);
		// 转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";
	}

	// addCategoryUI
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return "/admin/category/add.jsp";
	}

	// addCategory
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取分类名称
		String cname = req.getParameter("cname");
		// 创建分类ID
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCid(id);
		c.setCname(cname);
		// 调用业务层添加分类功能
		CategoryService CategoryService = new CategoryServiceImpl();
		CategoryService.addCategory(c);
		// 重定向到查询全部分类信息
		resp.sendRedirect("/store/AdminCategoryServlet?method=findAllCates");
		return null;
	}
}

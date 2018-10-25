package cn.zx.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.store.domain.Product;
import cn.zx.store.service.ProductService;
import cn.zx.store.service.serviceImpl.ProductServiceImpl;
import cn.zx.store.web.base.BaseServlet;


public class IndexSerevlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
      
	//查询全部分类
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//调用业务层功能：获取全部分类信息，返回集合
		//CategoryService categoryService = new CategoryServiceImpl();
		//List<Category> list = categoryService.getAllCates();
		//将返回的集合放入request
		//request.setAttribute("allCates", list);
		
		//调用业务层查询最新商品和最热商品
		ProductService productService = new ProductServiceImpl();
		List<Product> list01 = productService.findHots();
		List<Product> list02 = productService.findNews();
		
		//将两个集合放入到request
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);
		//转发到真实的首页
		return "/jsp/index.jsp";
	}


}

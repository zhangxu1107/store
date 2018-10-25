package cn.zx.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.store.domain.Category;
import cn.zx.store.domain.PageModel;
import cn.zx.store.domain.Product;
import cn.zx.store.service.ProductService;
import cn.zx.store.service.serviceImpl.ProductServiceImpl;
import cn.zx.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findProductById 按商品id查找商品
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取商品pid
		String pid = request.getParameter("pid");
		
		// 根据商品pid查询商品信息
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		// 将获取到的商品放入request
		request.setAttribute("product", product);
		// 转发到/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}

	// findProductsByCidWithPage 按商品id分页查询商品
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		// 获取cid,num
		String cid = request.getParameter("cid");
		System.out.println(cid);
		String cname = request.getParameter("cname");
		System.out.println(cname);
		request.setAttribute("cname", cname);
		int curNum = Integer.parseInt(request.getParameter("num"));

		// 调用业务层功能:以分页形式查询当前类别下商品信息
		ProductService productService = new ProductServiceImpl();
		// 返回PageModel对象(1_当前页商品信息2_分页3_url)
		PageModel pm = productService.findProductsByCidWithPage(cid, curNum);

		// 将PageModel对象放入request
		request.setAttribute("page", pm);
		// 转发到/jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}

}

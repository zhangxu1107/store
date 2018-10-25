package cn.zx.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.store.domain.Category;
import cn.zx.store.service.CategoryService;
import cn.zx.store.service.ProductService;
import cn.zx.store.service.serviceImpl.CategoryServiceImpl;
import cn.zx.store.service.serviceImpl.ProductServiceImpl;
import cn.zx.store.utils.JedisUtils;
import cn.zx.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;


public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       

	//findAllCates 找到所有商品分类
	public String findAllCates(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取redis中的全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCates");
		if(null == jsonStr || "".equals(jsonStr)) {
			//调用业务层获取全部分类
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> list = categoryService.getAllCates();
			//将全部分类转换为JSON格式的数据
			jsonStr = JSONArray.fromObject(list).toString();
			System.out.println(jsonStr);
			
			//将获取到的JSON格式数据存入Redis
			jedis.set("allCates", jsonStr);
			System.out.println("redis缓存中没有数据");
			//将全部分类信息响应到客户端
			response.setContentType("application/json;charset=utf-8"); //告诉浏览器本次响应的是JSON格式的字符串
			response.getWriter().print(jsonStr);
			
		}else {
			System.out.println("redis缓存中有数据");
			//将全部分类信息响应到客户端
			response.setContentType("application/json;charset=utf-8"); //告诉浏览器本次响应的是JSON格式的字符串
			response.getWriter().print(jsonStr);
		}
		
		//释放Jedis资源
		JedisUtils.closeJedis(jedis);
		return null;
	}

}


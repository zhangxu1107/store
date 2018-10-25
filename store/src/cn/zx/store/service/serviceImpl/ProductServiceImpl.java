package cn.zx.store.service.serviceImpl;

import java.util.List;

import cn.zx.store.dao.ProductDao;
import cn.zx.store.domain.PageModel;
import cn.zx.store.domain.Product;
import cn.zx.store.service.ProductService;
import cn.zx.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	ProductDao productDao = (ProductDao) BeanFactory.createObject("ProductDao");

	// 实现查找最热商品
	@Override
	public List<Product> findHots() throws Exception {
		return productDao.findHots();
	}

	// 实现查找最新商品
	@Override
	public List<Product> findNews() throws Exception {
		return productDao.findNews();
	}

	// 实现按照商品id查询商品
	@Override
	public Product findProductByPid(String pid) throws Exception {
		return productDao.findProductByPid(pid);
	}

	// 实现按商品分类id分页查询商品
	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
		// 1_创建PageModel对象 目的:计算分页参数
		// 统计当前分类下商品个数 select count(*) from product where cid=?
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		// 2_关联集合 select * from product where cid =? limit ? ,?
		List<Product> list = productDao.findProductsByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 3_关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid=" + cid);
		return pm;
	}

	// 实现按分页查询所有商品
	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		// 1_创建对象
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		// 2_关联集合 select * from product limit ? , ?
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 3_关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	// 实现添加商品
	@Override
	public void saveProduct(Product product) throws Exception {
		productDao.saveProduct(product);
	}

}

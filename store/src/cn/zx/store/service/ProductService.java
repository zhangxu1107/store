package cn.zx.store.service;

import java.util.List;

import cn.zx.store.domain.PageModel;
import cn.zx.store.domain.Product;

public interface ProductService {

	// 找到热门商品
	List<Product> findHots() throws Exception;

	// 找到新商品
	List<Product> findNews() throws Exception;

	// 按照商品id查询商品
	Product findProductByPid(String pid) throws Exception;

	// 按商品分类id分页查询商品
	PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception;

	// 按分页查询所有商品
	PageModel findAllProductsWithPage(int curNum) throws Exception;

	//添加商品
	void saveProduct(Product product) throws Exception;

}

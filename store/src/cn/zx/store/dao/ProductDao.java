package cn.zx.store.dao;

import java.util.List;

import cn.zx.store.domain.Product;

public interface ProductDao {

	// 找到热门商品
	List<Product> findHots() throws Exception;

	// 找到新商品
	List<Product> findNews() throws Exception;

	// 按照商品id查询商品
	Product findProductByPid(String pid) throws Exception;

	// 统计当前分类下商品个数
	int findTotalRecords(String cid) throws Exception;

	// 按商品分类id分页查询商品
	List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception;

	// 查詢商品总数
	int findTotalRecords() throws Exception;

	// 按分页查询所有商品
	List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception;

	// 添加商品
	void saveProduct(Product product)  throws Exception ;
}

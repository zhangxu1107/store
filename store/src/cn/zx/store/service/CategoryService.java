package cn.zx.store.service;

import java.util.List;

import cn.zx.store.domain.Category;

public interface CategoryService {

	//获取全部商品分类
	List<Category> getAllCates() throws Exception;

	void addCategory(Category c)  throws Exception;


}

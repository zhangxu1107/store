package cn.zx.store.dao;

import java.util.List;

import cn.zx.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCates() throws Exception;

	void addCategory(Category c) throws Exception;

}

package cn.zx.store.service.serviceImpl;

import java.util.List;

import cn.zx.store.dao.CategoryDao;
import cn.zx.store.domain.Category;
import cn.zx.store.service.CategoryService;
import cn.zx.store.utils.BeanFactory;
import cn.zx.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	CategoryDao categoryDao = (CategoryDao) BeanFactory.createObject("CategoryDao");
	
	//实现获取全部商品分类
	@Override
	public List<Category> getAllCates() throws Exception {
		return categoryDao.getAllCates();
	}

	@Override
	public void addCategory(Category c) throws Exception {
		//本质是向MYSQL插入一条数据
		categoryDao.addCategory(c);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCates");
		JedisUtils.closeJedis(jedis);
	}
}

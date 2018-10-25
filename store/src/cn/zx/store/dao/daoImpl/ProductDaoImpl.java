package cn.zx.store.dao.daoImpl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.zx.store.dao.ProductDao;
import cn.zx.store.domain.Product;
import cn.zx.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	// 实现查找最热商品
	@Override
	public List<Product> findHots() throws Exception {
		String sql = "select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0 , 9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	// 实现查找最新商品
	@Override
	public List<Product> findNews() throws Exception {
		String sql = "select * from product where pflag=0 order by pdate desc limit 0 , 9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	// 实现按照商品id查询商品
	@Override
	public Product findProductByPid(String pid) throws Exception {
		String sql = "select * from product where pid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	// 实现统计当前分类下商品个数
	@Override
	public int findTotalRecords(String cid) throws Exception {
		String sql = "select count(*) from product where cid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) qr.query(sql, new ScalarHandler(), cid);
		return num.intValue();
	}

	// 实现按商品分类id分页查询商品
	@Override
	public List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {
		String sql = "select * from product where cid= ? limit ? , ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
	}

	// 查詢商品总数
	@Override
	public int findTotalRecords() throws Exception {
		String sql="select count(*) from product";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		Long num=(Long)qr.query(sql, new ScalarHandler());
		return num.intValue();
	}

	// 实现按分页查询所有商品
	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
		String sql="select * from product order by pdate desc limit  ? , ?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class),startIndex,pageSize);
	}

	// 实现添加商品
	@Override
	public void saveProduct(Product product)  throws Exception {
		String sql="INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		Object[] params={product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		qr.update(sql,params);
	}

}

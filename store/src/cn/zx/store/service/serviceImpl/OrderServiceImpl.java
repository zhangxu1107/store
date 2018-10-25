package cn.zx.store.service.serviceImpl;


import java.sql.Connection;
import java.util.List;

import cn.zx.store.dao.OrderDao;
import cn.zx.store.domain.Order;
import cn.zx.store.domain.OrderItem;
import cn.zx.store.domain.PageModel;
import cn.zx.store.domain.User;
import cn.zx.store.service.OrderService;
import cn.zx.store.utils.BeanFactory;
import cn.zx.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	OrderDao orderDao = (OrderDao) BeanFactory.createObject("OrderDao");

	// 实现订单保存
	@Override
	public void saveOrder(Order order) throws Exception {
		// 保存订单和订单下所有的订单项(同时成功,失败)
		
/*		try {
			JDBCUtils.startTransaction();
			OrderDao orderDao = new OrderDaoImpl();
			orderDao.saveOrder(order);
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
		}*/

		
		

		Connection conn=null;
		try {
			//获取连接
			conn=JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			
			orderDao.saveOrder(conn,order);
			//保存订单项
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn,item);	
			}
			//提交
			conn.commit();
		} catch (Exception e) {
			//回滚
			conn.rollback();
		}
		
	}

	// 实现分页查询我的订单
	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		// 1_创建PageModel对象,目的:计算并且携带分页参数
		// select count(*) from orders where uid=?
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		// 2_关联集合 select * from orders where uid=? limit ? ,?
		List list = orderDao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 3_关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	//实现通过订单号查找订单
	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}

	// 更新订单
	@Override
	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
	}


	// 实现查询所有订单
	@Override
	public List<Order> findAllOrders() throws Exception {
		return orderDao.findAllOrders();
	}

	// 按订单状态所有订单
	@Override
	public List<Order> findAllOrders(String st) throws Exception{
		return orderDao.findAllOrders(st);
	}

}

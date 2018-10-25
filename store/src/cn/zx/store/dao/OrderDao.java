package cn.zx.store.dao;

import java.sql.Connection;
import java.util.List;

import cn.zx.store.domain.Order;
import cn.zx.store.domain.OrderItem;
import cn.zx.store.domain.User;

public interface OrderDao {

	void saveOrder(Connection conn, Order order) throws Exception;

	void saveOrderItem(Connection conn, OrderItem item) throws Exception;

	int getTotalRecords(User user) throws Exception;

	List<Order> findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception;

	// 通过订单号查找订单
	Order findOrderByOid(String oid) throws Exception;

	// 更新订单
	void updateOrder(Order order) throws Exception;

	// 查询所有订单
	List<Order> findAllOrders() throws Exception;

	// 按订单状态所有订单
	List<Order> findAllOrders(String st) throws Exception;

}

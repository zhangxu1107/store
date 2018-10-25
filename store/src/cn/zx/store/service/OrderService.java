package cn.zx.store.service;

import java.util.List;

import cn.zx.store.domain.Order;
import cn.zx.store.domain.PageModel;
import cn.zx.store.domain.User;

public interface OrderService {

	// 将购物车中的信息以订单的形式保存
	void saveOrder(Order order) throws Exception;

	// 分页查询我的订单
	PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

	// 通过订单号查找订单
	Order findOrderByOid(String oid) throws Exception;

	// 更新订单
	void updateOrder(Order order) throws Exception;

	// 查询所有订单
	List<Order> findAllOrders() throws Exception;

	// 按订单状态所有订单
	List<Order> findAllOrders(String st) throws Exception;

}

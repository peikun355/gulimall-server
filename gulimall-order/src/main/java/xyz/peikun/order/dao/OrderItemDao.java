package xyz.peikun.order.dao;

import xyz.peikun.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:49:57
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}

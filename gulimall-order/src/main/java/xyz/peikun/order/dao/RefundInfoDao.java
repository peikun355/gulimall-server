package xyz.peikun.order.dao;

import xyz.peikun.order.entity.RefundInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:49:57
 */
@Mapper
public interface RefundInfoDao extends BaseMapper<RefundInfoEntity> {
	
}

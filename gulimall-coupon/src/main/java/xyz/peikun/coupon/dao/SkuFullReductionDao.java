package xyz.peikun.coupon.dao;

import xyz.peikun.coupon.entity.SkuFullReductionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品满减信息
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:28:39
 */
@Mapper
public interface SkuFullReductionDao extends BaseMapper<SkuFullReductionEntity> {
	
}

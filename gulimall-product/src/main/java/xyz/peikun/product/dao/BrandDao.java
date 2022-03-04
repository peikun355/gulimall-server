package xyz.peikun.product.dao;

import xyz.peikun.product.entity.BrandEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 08:23:21
 */
@Mapper
public interface BrandDao extends BaseMapper<BrandEntity> {
	
}

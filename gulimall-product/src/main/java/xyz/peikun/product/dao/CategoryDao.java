package xyz.peikun.product.dao;

import xyz.peikun.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 08:23:21
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}

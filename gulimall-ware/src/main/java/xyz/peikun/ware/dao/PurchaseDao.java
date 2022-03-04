package xyz.peikun.ware.dao;

import xyz.peikun.ware.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:54:04
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}

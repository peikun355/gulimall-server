package xyz.peikun.ware.dao;

import xyz.peikun.ware.entity.WareInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息
 * 
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:54:04
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfoEntity> {
	
}

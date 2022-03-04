package xyz.peikun.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.product.entity.SpuInfoEntity;
import xyz.peikun.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 08:23:21
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProduct(SpuSaveVo vo);
}


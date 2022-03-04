package xyz.peikun.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:54:04
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


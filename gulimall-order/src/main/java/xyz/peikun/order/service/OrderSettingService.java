package xyz.peikun.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:49:57
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


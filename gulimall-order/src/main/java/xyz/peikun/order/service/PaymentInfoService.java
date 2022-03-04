package xyz.peikun.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.order.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * 支付信息表
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:49:57
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


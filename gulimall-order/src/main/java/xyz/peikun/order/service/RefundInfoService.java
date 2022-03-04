package xyz.peikun.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:49:57
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


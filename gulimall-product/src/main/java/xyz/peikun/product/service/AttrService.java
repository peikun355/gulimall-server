package xyz.peikun.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.product.entity.AttrEntity;
import xyz.peikun.product.vo.AttrVo;
import xyz.peikun.product.vo.RespAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 08:23:21
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(AttrVo attr);

    PageUtils queryPageDetail(Map<String, Object> params, Long catelogId, String attrType);

    RespAttrVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getAttrRelation(Long attrgroupId);
}


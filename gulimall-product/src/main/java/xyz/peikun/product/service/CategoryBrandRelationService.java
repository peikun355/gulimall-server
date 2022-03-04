package xyz.peikun.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.product.entity.CategoryBrandRelationEntity;
import xyz.peikun.product.vo.BrandVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 08:23:21
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    List<BrandVo> getBrandList(Long catId);
}


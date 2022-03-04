package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.product.dao.BrandDao;
import xyz.peikun.product.entity.BrandEntity;
import xyz.peikun.product.entity.CategoryBrandRelationEntity;
import xyz.peikun.product.service.BrandService;
import xyz.peikun.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;
import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)){
            wrapper.eq("brand_id",key);
            wrapper.or().like("name",key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 级联修改关联表的品牌名
     * @param brand
     */
    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {
        //修改自己
        this.updateById(brand);
        //如果修改了品牌名，根据品牌id修改关联表的品牌名字
        CategoryBrandRelationEntity relation = new CategoryBrandRelationEntity();
        relation.setBrandName(brand.getName());
        categoryBrandRelationService.update(relation,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brand.getBrandId()));
    }

}
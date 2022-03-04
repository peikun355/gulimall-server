package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.product.dao.CategoryBrandRelationDao;
import xyz.peikun.product.entity.BrandEntity;
import xyz.peikun.product.entity.CategoryBrandRelationEntity;
import xyz.peikun.product.entity.CategoryEntity;
import xyz.peikun.product.service.BrandService;
import xyz.peikun.product.service.CategoryBrandRelationService;
import xyz.peikun.product.service.CategoryService;
import xyz.peikun.product.vo.BrandVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        BrandEntity brand=brandService.getById(categoryBrandRelation.getBrandId());
        CategoryEntity category = categoryService.getById(categoryBrandRelation.getCatelogId());
        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(category.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public List<BrandVo> getBrandList(Long catId) {
        List<CategoryBrandRelationEntity> relationEntities = baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<BrandVo> brandVos = relationEntities.stream().map(relation -> {
            BrandVo brandVo = new BrandVo();
            BeanUtils.copyProperties(relation, brandVo);
            return brandVo;
        }).collect(Collectors.toList());
        return brandVos;
    }

}
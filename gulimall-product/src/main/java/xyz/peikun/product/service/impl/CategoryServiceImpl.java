package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.product.dao.CategoryBrandRelationDao;
import xyz.peikun.product.dao.CategoryDao;
import xyz.peikun.product.entity.CategoryEntity;
import xyz.peikun.product.service.CategoryService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Resource
    CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listTree() {
        //得到所有菜单
        List<CategoryEntity> list = baseMapper.selectList(null);

        //得到一级分类列表
        List<CategoryEntity> firstCategory =
                list.stream().filter(categoryEntity ->
                        categoryEntity.getParentCid() == 0)
                        //映射一级分类列表得到子菜单
                        .map(menu->{
                            menu.setChildren(getChildren(menu,list));
                            return menu;
                        })
                        .sorted((menu1,menu2)->{
                            Integer sort1 = menu1.getSort();
                            Integer sort2 = menu2.getSort();
                            return (sort1==null?0:sort1)-(sort2==null?0:sort2);
                        })
                        .collect(Collectors.toList());
        //返回一级菜单
        return firstCategory;
    }

    @Override
    public void removeMenuByIds(Long[] catIds) {
        // TODO 如果菜单被引用，不可删除

        baseMapper.deleteBatchIds(Arrays.asList(catIds));
    }

    //根据三级分类id返回完整路径数组
    @Override
    public Long[] getCatelogIdPath(Long catelogId) {
        List<Long> paths=new ArrayList<>();
        List<Long> catelogIdPath = findParentId(catelogId,paths);
        Collections.reverse(catelogIdPath);

        return catelogIdPath.toArray(new Long[0]);
    }

    @Override
    @Transactional
    public void updateDetail(CategoryEntity category) {
        Long cId=category.getCatId();
        String name=category.getName();
        //更新自己
        this.updateById(category);
        //更新关联表
        categoryBrandRelationDao.updateDetail(cId,name);

    }

    //递归方式查找完整路径
    private List<Long> findParentId(Long catelogId,List<Long> paths){
        paths.add(catelogId);

        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid()!=0){
            findParentId(byId.getParentCid(),paths);
        }
        return paths;
    }
    //递归方式查找所有菜单菜单的子菜单
    public List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> list = all.stream().filter(categoryEntity ->
                //过滤出所有子菜单
                categoryEntity.getParentCid() == root.getCatId())
                //找出所有子菜单的子菜单
                .map(towCategory -> {
                    //获取三级分类
                    towCategory.setChildren(this.getChildren(towCategory,all));
                    return towCategory;
                })
                .sorted((menu1,menu2)->{
                    Integer sort1 = menu1.getSort();
                    Integer sort2 = menu2.getSort();
                    return (sort1==null?0:sort1)-(sort2==null?0:sort2);
                })
                .collect(Collectors.toList());
        return list;
    }

}
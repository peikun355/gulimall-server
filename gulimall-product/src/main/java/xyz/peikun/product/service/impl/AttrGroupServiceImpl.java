package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.peikun.common.constant.ProductConstant;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.product.dao.AttrGroupDao;
import xyz.peikun.product.entity.AttrAttrgroupRelationEntity;
import xyz.peikun.product.entity.AttrEntity;
import xyz.peikun.product.entity.AttrGroupEntity;
import xyz.peikun.product.service.AttrAttrgroupRelationService;
import xyz.peikun.product.service.AttrGroupService;
import xyz.peikun.product.service.AttrService;
import xyz.peikun.product.vo.AttrGroupWithAttrVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    AttrService attrService;
    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer catelogId) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) {//如果有key的话
            wrapper.and(obj -> {
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }

        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        } else {

            wrapper.eq("catelog_id", catelogId);

            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }
    }

    /**
     * 根据分组id 获取属性分组里面还没有关联的本分类里面的其他基本属性，方便添加新的关联
     *
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils noAttrRelation(Map<String, Object> params, Long attrgroupId) {
        //1.获取当前分类的id
        AttrGroupEntity attrGroupEntity = baseMapper.selectById(attrgroupId);
        Long catlogId=attrGroupEntity.getCatelogId();

        //1.查出当前分类下其他所有分组
        List<AttrGroupEntity> groupEntities = baseMapper.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catlogId));
        //2.查出这些分组关联的属性ID集合
        //1)其他分组id集合
        List<Long> groupIds = groupEntities.stream().map((AttrGroupEntity::getAttrGroupId)).collect(Collectors.toList());
        //查出所有其他被关联的条目
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        //映射出被其他分组关联的属性id集合
        List<Long> noAttrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        //3.查询当前分类下不包含被引用的所有基本属性
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catlogId).eq("attr_type", ProductConstant.attr.ATTR_TYPE_BASE.getCode());
        String key= (String) params.get("key");
        if (StringUtils.isNotBlank(key)){
            wrapper.eq("attr_id",key).or().like("attr_name",key);
        }
        //如果被其他分组关联就拼接
        if (!noAttrIds.isEmpty()){
            wrapper.notIn("attr_id", noAttrIds);
        }

        IPage<AttrEntity> page = attrService.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }

    //根据分类id获取所有属性分组和所分组下所有属性
    @Override
    public List<AttrGroupWithAttrVo> getAttrGroupWithAttrByCatId(Long catelogId) {
        //1.根据id获取所有属性分组
        List<AttrGroupEntity> groupEntities = baseMapper.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //获取所有属性分组下的所有属性
        List<AttrGroupWithAttrVo> collect = groupEntities.stream().map(group -> {
            AttrGroupWithAttrVo attrVo = new AttrGroupWithAttrVo();
            BeanUtils.copyProperties(group, attrVo);
            //获取当前属性分组关联所有属性的条目
            List<AttrEntity> attrEntities=attrService.getAttrRelation(group.getAttrGroupId());
            attrVo.setAttrs(attrEntities);
            return attrVo;
        }).collect(Collectors.toList());

        return collect;
    }
}
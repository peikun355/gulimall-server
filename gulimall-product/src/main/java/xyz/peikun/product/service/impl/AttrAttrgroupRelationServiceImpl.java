package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.product.dao.AttrAttrgroupRelationDao;
import xyz.peikun.product.entity.AttrAttrgroupRelationEntity;
import xyz.peikun.product.service.AttrAttrgroupRelationService;
import xyz.peikun.product.vo.AttrRelationVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteAttrRelation(List<AttrRelationVo> relationVos) {
        //根据属性id和属性分组id删除关联表中的数据
        List<AttrAttrgroupRelationEntity> entities = relationVos.stream().map((attrRelationVo -> {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrRelationVo, relation);
            return relation;
        })).collect(Collectors.toList());
        baseMapper.deleteAttrRelation(entities);
    }

    //根据vo集合添加关联关系
    @Override
    public void SaveAttrRelation(List<AttrRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> relationEntities = vos.stream().map((vo -> {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(vo, relation);
            return relation;
        })).collect(Collectors.toList());
        this.saveBatch(relationEntities);
    }

}
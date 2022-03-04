package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.peikun.common.constant.ProductConstant;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.product.dao.AttrAttrgroupRelationDao;
import xyz.peikun.product.dao.AttrDao;
import xyz.peikun.product.dao.AttrGroupDao;
import xyz.peikun.product.dao.CategoryDao;
import xyz.peikun.product.entity.AttrAttrgroupRelationEntity;
import xyz.peikun.product.entity.AttrEntity;
import xyz.peikun.product.entity.AttrGroupEntity;
import xyz.peikun.product.entity.CategoryEntity;
import xyz.peikun.product.service.AttrService;
import xyz.peikun.product.service.CategoryService;
import xyz.peikun.product.vo.AttrVo;
import xyz.peikun.product.vo.RespAttrVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    AttrGroupDao attrGroupDao;

    @Resource
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveDetail(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //添加自己的信息
        this.save(attrEntity);
        //如果商品属性类型是基本属性的话 添加关联表信息
        if (attr.getAttrType() == ProductConstant.attr.ATTR_TYPE_BASE.getCode()){
            Long attrId = attrEntity.getAttrId();
            Long attrGroupId = attr.getAttrGroupId();
            //如果关联了属性分组，插入一条关联表记录
            if (attrGroupId!=null){
                AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
                relation.setAttrId(attrId);
                relation.setAttrGroupId(attrGroupId);
                attrAttrgroupRelationDao.insert(relation);
            }
        }

    }

    @Override
    public PageUtils queryPageDetail(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type","base".equalsIgnoreCase(attrType)?1:0);
        String key= (String) params.get("key");
        //如果查询特定分类
        if (catelogId!=0){
            wrapper.eq("catelog_id",catelogId);
        }
        //如果检索条件不为0
        if (StringUtils.isNotBlank(key)){
            wrapper.and(obj->{
                obj.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils=new PageUtils(page);
        List<AttrEntity> records = page.getRecords();

        List<RespAttrVo> voList = records.stream().map(attrEntity -> {
            RespAttrVo attrVo = new RespAttrVo();
            BeanUtils.copyProperties(attrEntity, attrVo);
            //如果商品属性类型是基本属性的话 获取当前属性的属性分组名
            if ("base".equalsIgnoreCase(attrType)){

                QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();

                queryWrapper.eq("attr_id",attrEntity.getAttrId());
                AttrAttrgroupRelationEntity relation = attrAttrgroupRelationDao.selectOne(queryWrapper);
                if (relation!=null && relation.getAttrGroupId()!=null){
                        Long attrGroupId = relation.getAttrGroupId();
                        attrVo.setAttrGroupId(attrGroupId);
                        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                        attrVo.setAttrGroupName(attrGroupEntity.getAttrGroupName());

                }
            }

            //获取当前属性的分类名
            if (attrEntity.getCatelogId()!=null){
                CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
                attrVo.setCatelogName(categoryEntity.getName());
            }

            return attrVo;
        }).collect(Collectors.toList());

        pageUtils.setList(voList);

        return pageUtils;
    }

    @Override
    public RespAttrVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        RespAttrVo attrVo = new RespAttrVo();
        BeanUtils.copyProperties(attrEntity,attrVo);
        //如果商品属性类型是基本属性的话 获取当前属性和属性分组关联对象
        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
        if (relation!=null&&attrEntity.getAttrType()==ProductConstant.attr.ATTR_TYPE_BASE.getCode()){
            attrVo.setAttrGroupId(relation.getAttrGroupId());
            AttrGroupEntity groupEntity = attrGroupDao.selectById(relation.getAttrGroupId());
            //获取属性分组名字
            if (groupEntity!=null){

                attrVo.setAttrGroupName(groupEntity.getAttrGroupName());

            }
        }

        //获取分类id
        Long catelogId = attrEntity.getCatelogId();
        if (catelogId!=null){
            //获取完整路径返回
            Long[] catelogIdPath = categoryService.getCatelogIdPath(catelogId);
            attrVo.setCatelogPath(catelogIdPath);
        }

        return attrVo;
    }

    @Override
    @Transactional
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //更新自己表数据
        this.updateById(attrEntity);
        //如果商品属性类型是基本属性的话 同步更新关联表数据
        if (attr.getAttrType()==ProductConstant.attr.ATTR_TYPE_BASE.getCode()){

            Long attrGroupId = attr.getAttrGroupId();
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            relation.setAttrGroupId(attrGroupId);
            relation.setAttrId(attr.getAttrId());
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (count>0){

                //根据属性id修改关联表中属性分组id
                attrAttrgroupRelationDao.update(relation,new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));

            }else {//走到这说明没有关联分组
                //新增
                attrAttrgroupRelationDao.insert(relation);
            }

        }

    }

    //获取指定分组关联的所有属性
    @Override
    public List<AttrEntity> getAttrRelation(Long attrgroupId) {
        //获取当前分组所有属性id
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        if (entities.isEmpty()){
            return null;
        }
        //如果关联表中有关联 映射出关联表中的属性id
        List<Long> attrIds = entities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        //根据属性id查询属性

        return this.listByIds(attrIds);
    }

}
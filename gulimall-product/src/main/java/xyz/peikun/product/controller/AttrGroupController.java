package xyz.peikun.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.R;
import xyz.peikun.product.entity.AttrEntity;
import xyz.peikun.product.entity.AttrGroupEntity;
import xyz.peikun.product.service.AttrAttrgroupRelationService;
import xyz.peikun.product.service.AttrGroupService;
import xyz.peikun.product.service.AttrService;
import xyz.peikun.product.service.CategoryService;
import xyz.peikun.product.vo.AttrGroupWithAttrVo;
import xyz.peikun.product.vo.AttrRelationVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 属性分组
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 08:54:59
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 获取属性分组关联分类
     * @param attrgroupId
     * @return
     */
    ///product/attrgroup/{attrgroupId}/attr/relation
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> data = attrService.getAttrRelation(attrgroupId);
        return R.ok().put("data",data);
    }

    ///product/attrgroup/{catelogId}/withattr
    //17、根据分类id获取分类下所有分组&关联属性
    @GetMapping("{catelogId}/withattr")
    public R attrGroupWithAttr(@PathVariable("catelogId") Long catelogId){
        List<AttrGroupWithAttrVo> vos = attrGroupService.getAttrGroupWithAttrByCatId(catelogId);
        return R.ok().put("data",vos);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Integer catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
          PageUtils page =attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        //得到指定三级分类的完整分类路径数组
        Long[] catelogIdPath=categoryService.getCatelogIdPath(attrGroup.getCatelogId());
        attrGroup.setCatelogIdPath(catelogIdPath);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    @PostMapping("/attr/relation")
    public R attrRelation(@RequestBody List<AttrRelationVo> vos){
    attrAttrgroupRelationService.SaveAttrRelation(vos);
     return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     * /product/attrgroup/attr/relation/delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    ///product/attrgroup/{attrgroupId}/noattr/relation
    @GetMapping("{attrgroupId}/noattr/relation")
    public R noAttrRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrgroupId){
        PageUtils page=attrGroupService.noAttrRelation(params,attrgroupId);
        return R.ok().put("page",page);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteAttrRelation(@RequestBody List<AttrRelationVo> relationVos){
        attrAttrgroupRelationService.deleteAttrRelation(relationVos);
        return R.ok();
    }
}

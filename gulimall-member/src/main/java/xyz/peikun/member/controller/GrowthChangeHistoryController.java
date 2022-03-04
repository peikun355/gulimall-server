package xyz.peikun.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.R;
import xyz.peikun.member.entity.GrowthChangeHistoryEntity;
import xyz.peikun.member.entity.MemberEntity;
import xyz.peikun.member.feign.CouponFeignService;
import xyz.peikun.member.service.GrowthChangeHistoryService;

import java.util.Arrays;
import java.util.Map;



/**
 * 成长值变化历史记录
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:39:43
 */
@RestController
@RequestMapping("member/growthchangehistory")
public class GrowthChangeHistoryController {
    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    @Autowired
    private CouponFeignService couponFeignService;
    /**
     * 列表
     */
    @RequestMapping("/coupons")
    public R commons(){
        R coupons = couponFeignService.memberCoupons();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");

        return R.ok().put("member",memberEntity).put("coupons",coupons.get("coupons"));
    }
    @RequestMapping("/list")
    //@RequiresPermissions("member:growthchangehistory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = growthChangeHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:growthchangehistory:info")
    public R info(@PathVariable("id") Long id){
		GrowthChangeHistoryEntity growthChangeHistory = growthChangeHistoryService.getById(id);

        return R.ok().put("growthChangeHistory", growthChangeHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:growthchangehistory:save")
    public R save(@RequestBody GrowthChangeHistoryEntity growthChangeHistory){
		growthChangeHistoryService.save(growthChangeHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:growthchangehistory:update")
    public R update(@RequestBody GrowthChangeHistoryEntity growthChangeHistory){
		growthChangeHistoryService.updateById(growthChangeHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:growthchangehistory:delete")
    public R delete(@RequestBody Long[] ids){
		growthChangeHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

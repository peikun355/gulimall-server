package xyz.peikun.coupon.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.peikun.coupon.entity.SeckillSessionEntity;
import xyz.peikun.coupon.service.SeckillSessionService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.R;



/**
 * 秒杀活动场次
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:28:39
 */
@RestController
@RequestMapping("coupons/seckillsession")
public class SeckillSessionController {
    @Autowired
    private SeckillSessionService seckillSessionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupons:seckillsession:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = seckillSessionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupons:seckillsession:info")
    public R info(@PathVariable("id") Long id){
		SeckillSessionEntity seckillSession = seckillSessionService.getById(id);

        return R.ok().put("seckillSession", seckillSession);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupons:seckillsession:save")
    public R save(@RequestBody SeckillSessionEntity seckillSession){
		seckillSessionService.save(seckillSession);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupons:seckillsession:update")
    public R update(@RequestBody SeckillSessionEntity seckillSession){
		seckillSessionService.updateById(seckillSession);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupons:seckillsession:delete")
    public R delete(@RequestBody Long[] ids){
		seckillSessionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

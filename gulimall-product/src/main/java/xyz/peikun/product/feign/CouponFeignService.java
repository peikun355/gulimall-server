package xyz.peikun.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.peikun.common.to.SkuReductionTo;
import xyz.peikun.common.to.SpuBoundTo;
import xyz.peikun.common.utils.R;

//指定远程项目地址
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @PostMapping("coupons/spubounds/save")
    R saveSpuBound(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("coupons/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}

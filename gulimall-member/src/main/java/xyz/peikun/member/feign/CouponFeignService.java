package xyz.peikun.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.peikun.common.utils.R;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupons/coupon/member/list")
    public R memberCoupons();
}

package xyz.peikun.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.peikun.common.to.MemberPrice;
import xyz.peikun.common.to.SkuReductionTo;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.coupon.dao.SkuFullReductionDao;
import xyz.peikun.coupon.entity.MemberPriceEntity;
import xyz.peikun.coupon.entity.SkuFullReductionEntity;
import xyz.peikun.coupon.entity.SkuLadderEntity;
import xyz.peikun.coupon.service.MemberPriceService;
import xyz.peikun.coupon.service.SkuFullReductionService;
import xyz.peikun.coupon.service.SkuLadderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    SkuFullReductionService skuFullReductionService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }


    //新增商品时保存优惠，满减等信息
    @Override
    public void saveInfo(SkuReductionTo to) {
        /**
         * 2.保存sku折扣信息  sms_sku_ladder
         */
        SkuLadderEntity ladderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(to, ladderEntity);
        ladderEntity.setAddOther(to.getCountStatus());
        if (ladderEntity.getFullCount() > 0 || ladderEntity.getDiscount().compareTo(new BigDecimal("0")) > 0) {
            skuLadderService.save(ladderEntity);
        }

        /**
         * 1.保存sku满减信息  sms_sku_full_reduction
         */

        SkuFullReductionEntity reductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(to, reductionEntity);
        if (reductionEntity.getReducePrice().compareTo(new BigDecimal("0")) > 0 || reductionEntity.getFullPrice().compareTo(new BigDecimal("0")) > 0) {
            skuFullReductionService.save(reductionEntity);
        }

        /**
         * 2.保存商品会员价格 sms_member_price
         */
        List<MemberPrice> memberPrice = to.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(mp -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(to.getSkuId());
            memberPriceEntity.setMemberLevelName(mp.getName());
            memberPriceEntity.setMemberLevelId(mp.getId());
            memberPriceEntity.setMemberPrice(mp.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(mp->{
            return mp.getMemberPrice().compareTo(new BigDecimal("0"))>0;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collect);
    }

}
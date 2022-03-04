package xyz.peikun.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.peikun.common.to.SkuReductionTo;
import xyz.peikun.common.to.SpuBoundTo;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.common.utils.Query;
import xyz.peikun.common.utils.R;
import xyz.peikun.product.dao.SpuInfoDao;
import xyz.peikun.product.entity.*;
import xyz.peikun.product.feign.CouponFeignService;
import xyz.peikun.product.service.*;
import xyz.peikun.product.vo.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SpuImagesService spuImagesService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存商品，大保存.这是一个事务
     * @param vo
     */
    @Transactional
    @Override
    public void saveProduct(SpuSaveVo vo) {
        /**
         *         1.保存商品sku基本信息 pms_spu_info
         */
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo,spuInfoEntity);
        this.save(spuInfoEntity);
        //添加成功后可获得spuid
        Long spuId = spuInfoEntity.getId();
        Long catelogId=spuInfoEntity.getCatalogId();
        Long brandId=spuInfoEntity.getBrandId();
        /**
         *        2.保存商品spu的描述信息图片 pms_spu_info_desc
         */
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuId);
        String decript = String.join(",", vo.getDecript());
        spuInfoDescEntity.setDecript(decript);
        spuInfoDescService.save(spuInfoDescEntity);
        /**
         *        3.保存商品spu图片信息 pms_spu_images
         */
        List<String> images = vo.getImages();
        spuImagesService.saveSpuImages(spuId,images);


        /**
         *        4.保存商品spu的规格参数 商品所有属性 pms_product_attr_value
         */
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        productAttrValueService.saveBaseAttrs(spuId,baseAttrs);

        //7.保存商品sku的积分信息 gulimall-->sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(spuId);

        R r = couponFeignService.saveSpuBound(spuBoundTo);
        if (r.getCode()!=0){
            log.error("远程调用保存商品sku的积分信息gulimall-->sms_spu_bounds失败！");
        }

        /**
         *        5.保存当前spu下所有sku信息
         */

        List<Skus> skus = vo.getSkus();
        skus.forEach(sku -> {
            String defaultImg="";
            for (Images image : sku.getImages()) {
                if (image.getDefaultImg()==1){
                    defaultImg=image.getImgUrl();
                }
            }
            //5.1: 收集sku基本信息
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(sku, skuInfoEntity);
            skuInfoEntity.setSpuId(spuId);
            skuInfoEntity.setBrandId(brandId);
            skuInfoEntity.setCatalogId(catelogId);
            skuInfoEntity.setSaleCount(0L);
            skuInfoEntity.setSkuDefaultImg(defaultImg);
            //5.1保存sku基本信息 pms_sku_info
            skuInfoService.save(skuInfoEntity);

            //收集sku的图片信息
            Long skuId=skuInfoEntity.getSkuId();
            List<SkuImagesEntity> imagesEntities = sku.getImages().stream().map(img -> {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                skuImagesEntity.setSkuId(skuId);
                skuImagesEntity.setImgUrl(img.getImgUrl());
                skuImagesEntity.setDefaultImg(img.getDefaultImg());
                return skuImagesEntity;
            }).filter(image-> StringUtils.isNotBlank(image.getImgUrl())).collect(Collectors.toList());
            //5.2:保存sku图片信息 pms_sku_images
            skuImagesService.saveBatch(imagesEntities);

            List<Attr> attr = sku.getAttr();
            List<SkuSaleAttrValueEntity> collect = attr.stream().map(a -> {
                SkuSaleAttrValueEntity valueEntity = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(a,valueEntity);
                valueEntity.setSkuId(skuId);
                return valueEntity;
            }).collect(Collectors.toList());
            //5.3: 保存sku销售属性信息 pms_sku_sale_attr_value
            skuSaleAttrValueService.saveBatch(collect);


            //5.4: sku的优惠，满减,折扣等信息 TODO
            SkuReductionTo skuReductionTo = new SkuReductionTo();
            BeanUtils.copyProperties(sku,skuReductionTo);
            skuReductionTo.setSkuId(skuId);
            R r1=couponFeignService.saveSkuReduction(skuReductionTo);
            if (r1.getCode()!=0){
                log.error("远程调用sku的优惠，满减,折扣等信息 失败！");
            }

            //5.5: sku的会员价格


        });





    }

}
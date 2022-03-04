/**
  * Copyright 2021 bejson.com 
  */
package xyz.peikun.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2021-10-30 20:10:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuSaveVo {
    /**
     * spu名称
     */
    private String spuName;
    //spu描述
    private String spuDescription;
    //分类id
    private Long catalogId;
    //品牌id
    private Long brandId;
    //商品重量
    private BigDecimal weight;
    //发布状态
    private int publishStatus;
    /**
     *商品描述集 长图片
     */
    private List<String> decript;
    //spu图片集
    private List<String> images;
    //积分
    private Bounds bounds;
    //基础属性
    private List<BaseAttrs> baseAttrs;
    //sku信息
    private List<Skus> skus;
}
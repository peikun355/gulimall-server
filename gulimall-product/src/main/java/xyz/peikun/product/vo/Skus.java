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
public class Skus {
    /**
     *当前商品的所有属性集合
     */
    private List<Attr> attr;
    /**
     * 当前版本（sku）名字
     */
    private String skuName;
    /**
     * sku价格
     */
    private BigDecimal price;
    /**
     * sku主标题
     */
    private String skuTitle;
    /**
     * sku副标题
     */
    private String skuSubtitle;
    /**
     * 当前版本（sku）图集
     */
    private List<Images> images;
    /**
     * 销售属性版本笛卡尔积形式的对应关系
     */
    private List<String> descar;
    /**
     * 满多少打折扣
     */
    private int fullCount;
    /**
     * 打多少折扣
     */
    private BigDecimal discount;
    /**
     * 计数状态
     */
    private int countStatus;
    /**
     * 满多少进行满减
     */
    private BigDecimal fullPrice;
    /**
     * 满减多少钱
     */
    private BigDecimal reducePrice;
    /**
     * 价格状态
     */
    private BigDecimal priceStatus;
    /**
     * 会员价格
     */
    private List<MemberPrice> memberPrice;

}
package xyz.peikun.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuReductionTo {

    private Long skuId;
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

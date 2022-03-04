package xyz.peikun.common.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuBoundTo {

    private Long spuId;
    /**
     * 金币积分/购买积分
     */
    private BigDecimal buyBounds;
    /**
     * 成长值
     */
    private BigDecimal growBounds;
}

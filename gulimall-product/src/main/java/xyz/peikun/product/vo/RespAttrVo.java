package xyz.peikun.product.vo;

import lombok.Data;
import xyz.peikun.product.entity.AttrEntity;

@Data
public class RespAttrVo extends AttrEntity {
    private String catelogName;
    private String attrGroupName;
    private Long attrGroupId;
    private Long[] catelogPath;
}

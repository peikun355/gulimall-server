/**
  * Copyright 2021 bejson.com 
  */
package xyz.peikun.product.vo;

import lombok.Data;

/**
 * Auto-generated: 2021-10-30 20:10:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class BaseAttrs {

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性值，可能为多个，以逗号分隔
     */
    private String attrValues;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】
     */
    private int showDesc;

}
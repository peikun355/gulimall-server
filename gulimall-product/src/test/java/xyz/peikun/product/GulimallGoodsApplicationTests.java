package xyz.peikun.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.peikun.product.entity.BrandEntity;
import xyz.peikun.product.service.BrandService;
import xyz.peikun.product.service.CategoryService;

import java.util.List;

@SpringBootTest
class GulimallGoodsApplicationTests {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        /*brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功");*/
        /*brandEntity.setBrandId(1L);
        brandEntity.setName("小米");
        brandService.updateById(brandEntity);*/
        List<BrandEntity> brand_id = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        for (BrandEntity entity : brand_id) {
            System.out.println(entity);
        }
    }

    @Test
    void parentPath(){
        Long[] catelogIdPath = categoryService.getCatelogIdPath(225L);
        for (Long aLong : catelogIdPath) {
            System.out.println(aLong);
        }
    }


}

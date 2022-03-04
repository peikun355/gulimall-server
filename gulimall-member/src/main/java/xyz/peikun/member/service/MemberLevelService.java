package xyz.peikun.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.peikun.common.utils.PageUtils;
import xyz.peikun.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author peikun
 * @email 1192316427@qq.com
 * @date 2021-10-13 19:39:43
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


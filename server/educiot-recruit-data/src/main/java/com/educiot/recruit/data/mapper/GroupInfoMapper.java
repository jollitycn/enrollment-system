package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.GroupInfo;
import com.educiot.recruit.data.entity.vo.ListGroupInfoPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生组信息表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface GroupInfoMapper extends BaseMapper<GroupInfo> {

    /**
     * 分页获取招生区域列表（指定招生学校下的）
     *
     * @param page            分页信息
     * @param recruitSchoolId 招生学校ID
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.ListGroupInfoPageVO&gt;
     * @author Pan Juncai
     * @date 2020/4/13 15:10
     */
    List<ListGroupInfoPageVO> listGroupInfoPage(IPage<ListGroupInfoPageVO> page,
                                                @Param("recruitSchoolId") Long recruitSchoolId);
}

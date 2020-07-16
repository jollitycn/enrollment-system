package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.Charger;
import com.educiot.recruit.data.entity.ext.ChargerExt;
import com.educiot.recruit.data.entity.vo.ChargerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生负责人 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface ChargerMapper extends BaseMapper<Charger> {

    /**
     * 查询指定分组下的负责人信息
     *
     * @param groupId 分组ID
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.ChargerQuery&gt;
     * @author Pan Juncai
     * @date 2020/4/15 17:16
     */
    List<ChargerVO> listChargerByGroupId(Long groupId);

    List<Charger> getAllChargerList(@Param("semesterId") Long semesterId);

    ChargerExt getByApiId(Long apiId, Long semesterId, Integer status);

    int getChargerCount(@Param("apiId") Long apiId,
                        @Param("semesterId") Long semesterId);
}

package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.Request;
import com.educiot.recruit.data.entity.query.AddRequestQuery;
import com.educiot.recruit.data.entity.query.RequestQuery;
import com.educiot.recruit.data.entity.vo.RequestVO;
import com.educiot.common.base.Result;

import java.util.List;

/**
 * <p>
 * 诉求表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
public interface IRequestService extends IService<Request> {

    /**
     * 根据公关ID查询诉求信息
     * @param requestQuery 查询诉求信息封装类
     * @return 返回诉求信息
     */
    Result<List<RequestVO>> queryRequestInfo(RequestQuery requestQuery);

    /**
     *添加或修改学生诉求信息
     * @param addRequestQuery 添加或修改学生诉求信息封装类
     * @return 返回添加或编辑是否成功状态
     */
    Result saveOrUpdateRequestInfo(AddRequestQuery addRequestQuery);

    /**
     * 修改诉求信息是否已处理状态
     * @param requestId 诉求id
     * @return 修改状态是否成功状态
     */
    Boolean updateRequestStatus(Long requestId);

}

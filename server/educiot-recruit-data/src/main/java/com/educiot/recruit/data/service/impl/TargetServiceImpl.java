package com.educiot.recruit.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.api.*;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.MyExcption;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.CommonUtil;
import com.educiot.recruit.data.entity.Group;
import com.educiot.recruit.data.entity.SysDictionary;
import com.educiot.recruit.data.entity.Target;
import com.educiot.recruit.data.entity.TargetDecomposition;
import com.educiot.recruit.data.entity.query.ListTargetPageQuery;
import com.educiot.recruit.data.entity.request.AddChannelOrMajorReq;
import com.educiot.recruit.data.entity.request.AddOrEditTargetReq;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;
import com.educiot.recruit.data.entity.vo.TagetTreeVO;
import com.educiot.recruit.data.entity.vo.TargetDetailVO;
import com.educiot.recruit.data.entity.vo.TargetVO;
import com.educiot.recruit.data.mapper.TargetMapper;
import com.educiot.recruit.data.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 目标 服务实现类
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-13
 */
@Service
@Log4j2
public class TargetServiceImpl extends ServiceImpl<TargetMapper, Target> implements ITargetService {

    @Autowired
    private ITargetDecompositionService targetDecompositionService;

    @Autowired
    private ISysDictionaryService sysDictionaryService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IRecruitSchoolService recruitSchoolService;

    @Autowired
    private TargetMapper targetMapper;


    /**
     * 分页获取招生目标列表
     *
     * @param param 分页参数封装
     * @return 返回目标列表分页数据
     */
    @Override
    public IPage<TargetVO> getTargetListPage(ListTargetPageQuery param,Long schoolId) {

        //构建page对象
        Page<TargetVO> page = new Page<>(param.getPageNum(), param.getPageSize());

        //DAO查询，按创建时间倒序，时间格式化注解实现 格式为 YYYY-MM-DD HH:mm:ss
        IPage<TargetVO> result=baseMapper.getTargetListPage(page,schoolId);

        return result;

    }

    /**
     * 添加或编辑招生目标
     *
     * @param addOrEditTargerReq 编辑招生目标参数封装
     * @return targetId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addOrEditTarget(AddOrEditTargetReq addOrEditTargerReq, Long recruitSchoolId,Long schoolId,Long userId,String userName, APIRequest apiRequest)
            throws Exception {

        //获取targetId参数
        Long targetId = addOrEditTargerReq.getTargetId();

        //根据targetId判断是新增还是更新
        if(!CommonUtil.checkId(targetId)){

            //判断该学校该学期下是否有目标，如果存在，则不允许新增
            Target checkTarget=this.checkTarget(recruitSchoolId,addOrEditTargerReq.getSemesterId());
            if(null!=checkTarget){
                return Long.valueOf(Constant.SYS_NEGATIVE_ONE);
            }

            Target target=new Target();

            //复制传入参数属性到实体bean
            BeanUtil.copyProperties(addOrEditTargerReq,target);

            //设置创建人
            target.setRecruitSchoolId(recruitSchoolId);
            target.setCreatorId(userId);
            target.setCreatorName(userName);

            //执行添加方法并为targetId赋值
            targetId=this.addTarget(target);

            if(!CommonUtil.checkId(targetId)){
                log.debug("添加招生目标数据插入错误~");
                return null;
            }

            //初始化渠道数据
            boolean channelResult=this.initChannel(targetId,recruitSchoolId,addOrEditTargerReq.getSemesterId());

            //初始化专业数据
            boolean majorResult = this.initMajor(apiRequest, targetId, recruitSchoolId);

            //如果数据初始化失败，抛出异常，让事务回滚
            if(!channelResult||!majorResult){
                throw new Exception();
            }

        }else {

            Target target=baseMapper.selectById(targetId);

            //复制传入参数属性到实体bean
            BeanUtil.copyProperties(addOrEditTargerReq,target);

            //设置更新人
            target.setModifyId(userId);
            target.setModifyName(userName);

            //执行更新方法
            boolean result = this.editTarget(target);
            if (!result) {
                return null;
            }

        }
        return targetId;
    }

    @Override
    public TargetDetailVO getDetail(Long targetId) {
        return baseMapper.getDetailByTargetId(targetId);
    }

    /**
     * 获取招生目标详情
     *
     * @param targetId 招生目标主键id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TargetDetailVO beforeGetDetail(Long targetId, Long recruitSchoolId) {
        // initMajor(apiRequest,targetId,recruitSchoolId);
        TargetDetailVO detail = null;
        try {
            detail = getDetail(targetId);
            //jason hong 增加线下渠道信息

            TagetTreeVO offlineChannel = detail.getChannelTreeVO().get(0);
            for (TagetTreeVO channel :
                    detail.getChannelTreeVO()) {
                if (Constant.UNDERLINE_TARGET.equals(channel.getNodeName())) {
                    offlineChannel = channel;
                }
            }
            List<TargetDecomposition> insertList = new ArrayList<TargetDecomposition>();
            List<TargetDecomposition> updateList = new ArrayList<TargetDecomposition>();
            List<TargetDecomposition> removeList = new ArrayList<TargetDecomposition>();
            List<Group> groupList = groupService.getTargetUnderLineData(detail.getSchoolId(), detail.getSemesterId());
            //添加线下渠道区域数据
            for (Group group : groupList) {
                //父id为父数据线下主键id
                TargetDecomposition targetDecomposition = new TargetDecomposition();
                targetDecomposition.setTargetDecompositionId(UUID.fastUUID().toString());
                targetDecomposition.setTargetId(targetId);
                targetDecomposition.setParentId(offlineChannel.getTargetDecompositionId());
                targetDecomposition.setType(Constant.SYS_ZERO);
                targetDecomposition.setNodeId(group.getGroupId().toString());
                targetDecomposition.setNodeName(group.getName());
                targetDecomposition.setTargetAmount(group.getTargetAmount());
                targetDecomposition.setComment(group.getComment());
                insertList.add(targetDecomposition);
            }
            if (offlineChannel.getNodeList().size() == 0) {
                //批量保存
                targetDecompositionService.saveBatch(insertList);

                offlineChannel.setNodeList(insertList);
            } else {
                //判断是否有分组需要删除
                if(insertList.size()!=0 && insertList.size() < offlineChannel.getNodeList().size()){
                    for(TargetDecomposition td : insertList){
                        for(TargetDecomposition tdg : offlineChannel.getNodeList()){
                            if (!td.getNodeName().equals(tdg.getNodeName())) {
                                removeList.add(tdg);
                                targetDecompositionService.removeById(tdg.getTargetDecompositionId());
                            }
                        }
                    }

                }else if(insertList.size()==0){

                    for(TargetDecomposition tdg : offlineChannel.getNodeList()){
                        targetDecompositionService.removeById(tdg.getTargetDecompositionId());
                    }

                }else{
                    //如果有数据的话，也要判断更新
                    for (TargetDecomposition td : offlineChannel.getNodeList()) {
                        insertList.forEach(tdg -> {
                            if (td.getNodeId() != null && td.getNodeId().equals(tdg.getNodeId())) {
                                updateList.add(tdg);
                            }
                        });
                    }
                    insertList.removeAll(updateList);
                    targetDecompositionService.saveBatch(insertList);


                    //批量保存
                    offlineChannel.getNodeList().addAll(insertList);
                }

            }


        } catch (Exception ex) {
            log.error(ex);
        }
        return detail;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean editChannelOrMajorNode(AddChannelOrMajorReq addChannelOrMajorReq) {
        //根据ID获取该节点对象
        TargetDecomposition targetDecomposition=targetDecompositionService.getById(
                addChannelOrMajorReq.getTargetDecompositionId());

        //将参数写入
        BeanUtil.copyProperties(addChannelOrMajorReq,targetDecomposition);

        //更新group表target_amount字段
        Integer type=targetDecomposition.getType();
        //如果是渠道类型节点
        if(type.equals(Constant.SYS_ZERO)){
            String nodeId=targetDecomposition.getNodeId();
            //如果节点id不为空
            if(StrUtil.isNotBlank(nodeId)){
                //如果存在区域组信息
                Group group=groupService.getById(nodeId);
                if(null!=group){
                    //更新区域组人数
                    group.setTargetAmount(addChannelOrMajorReq.getTargetAmount());
                    groupService.updateById(group);
                }
            }
        }

        //进行子节点更新
        return targetDecompositionService.updateById(targetDecomposition);
    }

    @Override
    protected Class<Target> currentModelClass() {
        return super.currentModelClass();
    }

    /**
     * 根据学校id和学期id获取招生目标，存在即返回目标对象，不存在返回空
     *
     * @param schoolId
     * @param semesterId
     * @return
     */
    @Override
    public Target checkTarget(Long schoolId, Long semesterId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq(Constant.Talble.RECRUIT_SCHOOL_ID,schoolId);
        queryWrapper.eq(Constant.Talble.SEMESTER_ID,semesterId);
        int checkTarget=baseMapper.selectCount(queryWrapper);
        if(checkTarget!=Constant.SYS_ZERO){
            return baseMapper.selectOne(queryWrapper);
        }else{
            return null;
        }

    }


/**
     * 新增target实体
     * @param target
     * @return 新增记录的主键id
     */
    private Long addTarget(Target target){
        target.setCreateTime(new Date());
        baseMapper.insert(target);
        return target.getTargetId();
    }

    /**
     * 更新target实体
     * @param target
     */
    private boolean editTarget(Target target) {
        target.setModifyTime(new Date());
        int i = baseMapper.updateById(target);
        return Constant.SYS_ONE == i;
    }


    @Override
    public TargetMapper getBaseMapper() {
        return super.getBaseMapper();
    }

    /**
     * 初始化目标渠道数据
     *
     * @param targetId        目标主键id
     * @param recruitSchoolId 招生学校主键id
     * @param semesterId      学期主键id
     */
    private boolean initChannel(Long targetId, Long recruitSchoolId, Long semesterId) throws MyExcption {

        //定义批量插入集合
        List<TargetDecomposition> insertList = new ArrayList<>();

        //创建父节点数据主键id
        String targetDecompositionIdLine = UUID.fastUUID().toString();
        String targetDecompositionIdUnderLine = UUID.fastUUID().toString();

        //添加二条渠道父数据
        TargetDecomposition targetDecompositionLine = new TargetDecomposition(targetDecompositionIdLine,
                targetId, Constant.SYS_ZERO_STR, Constant.SYS_ZERO, null, Constant.ONLINE_TARGET,
                Constant.SYS_ZERO, null);
        TargetDecomposition targetDecompositionUnderLine = new TargetDecomposition(targetDecompositionIdUnderLine,
                targetId, Constant.SYS_ZERO_STR, Constant.SYS_ZERO, null,
                Constant.UNDERLINE_TARGET, Constant.SYS_ZERO, null);
        insertList.add(targetDecompositionLine);
        insertList.add(targetDecompositionUnderLine);

        //在系统字典查询出指定学校的线上招生渠道数据
        QueryWrapper queryWrapperOne = new QueryWrapper();
        queryWrapperOne.eq(Constant.Talble.RECRUIT_SCHOOL_ID, recruitSchoolId);
        queryWrapperOne.eq(Constant.Talble.DATA_CODE, Constant.Dictionary.ONLINE_TARGET);
        SysDictionary dictionary = sysDictionaryService.getOne(queryWrapperOne);
        if (dictionary == null) {
            throw new MyExcption(CodeMsg.DIC_NOT_FOUND);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Constant.Talble.RECRUIT_SCHOOL_ID, recruitSchoolId);
        queryWrapper.eq(Constant.Talble.PARENT_ID, dictionary.getDictionaryId());
        List<SysDictionary> dictionaryList = sysDictionaryService.list(queryWrapper);


        //添加线上渠道字典数据
        for (SysDictionary sysDictionary : dictionaryList) {
            //父id为父数据线上主键id
            TargetDecomposition targetDecomposition = new TargetDecomposition(UUID.fastUUID().toString(),
                    targetId,targetDecompositionIdLine,Constant.SYS_ZERO,null,
                    sysDictionary.getDataValue(), Constant.SYS_ZERO,null);
            insertList.add(targetDecomposition);
        }

        //查询线下渠道区域数据
        List<Group> groupList=groupService.getTargetUnderLineData(recruitSchoolId,semesterId);

        //添加线下渠道区域数据
        for (Group group:groupList) {
            //父id为父数据线下主键id
            TargetDecomposition targetDecomposition=new TargetDecomposition(UUID.fastUUID().toString(),
                    targetId,targetDecompositionIdUnderLine,Constant.SYS_ZERO,group.getGroupId().toString(),
                    group.getName(),Constant.SYS_ZERO,null);
            insertList.add(targetDecomposition);
        }

        //批量保存
        return targetDecompositionService.saveBatch(insertList);

    }

    /**
     * 初始化目标专业数据
     *
     * @param targetId        目标主键id
     * @param recruitSchoolId 招生学校主键id
     */
    private boolean initMajor(APIRequest apiRequest, Long targetId, Long recruitSchoolId) throws MyExcption {
        List<String> removeByIds = new ArrayList<String>();
        /*
        HashMap paramMap=new HashMap();
        paramMap.put("schoolid",recruitSchoolId);
        //链式构建请求
        String result2 = HttpRequest.post(Constant.RequestURL.GET_COLLEGR)
                //头信息，多个头信息多次调用此方法即可
                .header(Header.USER_AGENT, "FDToken")
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        Console.log(result2);
        //查询接口 http://192.168.1.31:8080/recruit/sys/depart/info

         */
//         查询api未通，暂时假数据
        TargetDetailVO target = getDetail(targetId);
        List<TagetTreeVO> majors = target.getMajorTreeVO();
//        APIReuqest
        RecruitSchoolVO recruitSchoolResult = recruitSchoolService.getById(recruitSchoolId.toString());
        if (recruitSchoolResult == null) {
            throw new MyExcption(CodeMsg.RECRUIT_SCHOOL_NOT_EXIST);
        }
        DepartInfoResult termInfo = apiRequest.departInfo(recruitSchoolResult.getApiId().toString());
        if (!termInfo.isSuccess()) {
            throw new MyExcption(CodeMsg.API_DEPT_INFO_FAIL);
        }


        //定义批量插入集合
        List<TargetDecomposition> insertList = new ArrayList<>();

        //写入父节点
        for (DepartInfo colle : termInfo.getData()) {
            String targetDecompositionId = UUID.fastUUID().toString();
            TargetDecomposition targetDecomposition = new TargetDecomposition(targetDecompositionId,
                    targetId, Constant.SYS_ZERO_STR, Constant.SYS_ONE, colle.getId(),
                    colle.getName(), Constant.SYS_ZERO, null);
            TagetTreeVO orgDept = null;
            for (TagetTreeVO t : majors) {
                removeByIds.add(t.getTargetDecompositionId());
                if (t.getNodeId().equals(colle.getId())) {
                    orgDept = (t);
                }
            }
            if (orgDept != null) {
                targetDecomposition.setTargetAmount(orgDept.getTargetAmount());
            }
            insertList.add(targetDecomposition);

            //根据院系id查询专业信息，有多少个院系查询几次
            MajorListResult majorList = apiRequest.majorList(colle.getId());
            if (!majorList.isSuccess()) {
                throw new MyExcption(CodeMsg.API_MAJOR_LIST_FAIL);
            }

            //写入子节点
            for (MajorInfo major : majorList.getData()) {

                TargetDecomposition orgMajor = null;
                if (orgDept != null) {
                    for (TargetDecomposition t : orgDept.getNodeList()) {
                        removeByIds.add(t.getTargetDecompositionId());
                        if (t.getNodeId().equals(major.getId())) {
                            orgMajor = (t);
                        }
                    }
                }
                TargetDecomposition targetDecompositionSub = new TargetDecomposition(UUID.fastUUID().toString(),
                        targetId, targetDecompositionId, Constant.SYS_ONE, major.getId(),
                        major.getName(), Constant.SYS_ZERO, null);
                if (orgMajor != null) {
                    orgMajor.setTargetAmount(orgMajor.getTargetAmount());
                }
                insertList.add(targetDecompositionSub);
            }
        }
        boolean result = targetDecompositionService.saveBatch(insertList);
        targetDecompositionService.removeByIds(removeByIds);
        //批量写入
        return result;
    }

//    /**
//     * 测试接口
//     * @param id
//     * @return
//     */
//    private List<RequestApiDTO> getMajorList(String id,APIRequest apiRequest){
//
//        //专业集合1
//        List<RequestApiDTO> major1 = new ArrayList<>();
//        major1.add(test3);
//        major1.add(test4);
//
//        //专业集合2
//        List<RequestApiDTO> major2 = new ArrayList<>();
//        major2.add(test5);
//        major2.add(test6);
//        if("1".equals(id)){
//            return major1;
//        }else {
//            return major2;
//        }
//    }


    @Override
    public List<String> queryNodeNameList(Long schoolId, Long semesterId) {
        String s = targetMapper.queryTargetDecompositionId(schoolId, semesterId);
        if(null != s || !"".equals(s)){
            return targetMapper.queryNodeNameList(s);
        }
        return null;
    }
}

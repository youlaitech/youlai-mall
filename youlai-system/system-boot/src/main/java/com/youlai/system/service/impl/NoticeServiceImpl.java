package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.exception.BusinessException;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.system.converter.NoticeConverter;
import com.youlai.system.enums.NoticePublishStatusEnum;
import com.youlai.system.enums.NoticeTargetEnum;
import com.youlai.system.mapper.NoticeMapper;
import com.youlai.system.model.bo.NoticeBO;
import com.youlai.system.model.dto.NoticeDTO;
import com.youlai.system.model.entity.Notice;
import com.youlai.system.model.entity.User;
import com.youlai.system.model.entity.UserNotice;
import com.youlai.system.model.form.NoticeForm;
import com.youlai.system.model.query.NoticePageQuery;
import com.youlai.system.model.vo.NoticeDetailVO;
import com.youlai.system.model.vo.NoticePageVO;
import com.youlai.system.model.vo.UserNoticePageVO;
import com.youlai.system.service.NoticeService;
import com.youlai.system.service.UserNoticeService;
import com.youlai.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通知公告服务实现类
 *
 * @author Theo
 * @since 2024-08-27 10:31
 */
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    private final NoticeConverter noticeConverter;

    private final UserNoticeService userNoticeService;

    private final UserService userService;

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 获取通知公告分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage< NoticePageVO >} 通知公告分页列表
     */
    @Override
    public IPage<NoticePageVO> getNoticePage(NoticePageQuery queryParams) {
        Page<NoticeBO> noticePage = this.baseMapper.getNoticePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return noticeConverter.toPageVo(noticePage);
    }

    /**
     * 获取通知公告表单数据
     *
     * @param id 通知公告ID
     * @return {@link NoticeForm} 通知公告表单对象
     */
    @Override
    public NoticeForm getNoticeFormData(Long id) {
        Notice entity = this.getById(id);
        return noticeConverter.toForm(entity);
    }

    /**
     * 新增通知公告
     *
     * @param formData 通知公告表单对象
     * @return {@link Boolean} 是否新增成功
     */
    @Override
    public boolean saveNotice(NoticeForm formData) {

        if (NoticeTargetEnum.SPECIFIED.getValue().equals(formData.getTargetType())) {
            List<String> targetUserIdList = formData.getTargetUserIds();
            if (CollectionUtil.isEmpty(targetUserIdList)) {
                throw new BusinessException("推送指定用户不能为空");
            }
        }
        Notice entity = noticeConverter.toEntity(formData);
        entity.setCreateBy(SecurityUtils.getUserId());
        return this.save(entity);
    }

    /**
     * 更新通知公告
     *
     * @param id       通知公告ID
     * @param formData 通知公告表单对象
     * @return {@link Boolean} 是否更新成功
     */
    @Override
    public boolean updateNotice(Long id, NoticeForm formData) {
        if (NoticeTargetEnum.SPECIFIED.getValue().equals(formData.getTargetType())) {
            List<String> targetUserIdList = formData.getTargetUserIds();
            if (CollectionUtil.isEmpty(targetUserIdList)) {
                throw new BusinessException("推送指定用户不能为空");
            }
        }

        Notice entity = noticeConverter.toEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除通知公告
     *
     * @param ids 通知公告ID，多个以英文逗号(,)分割
     * @return {@link Boolean} 是否删除成功
     */
    @Override
    @Transactional
    public boolean deleteNotices(String ids) {
        if (StrUtil.isBlank(ids)) {
            throw new BusinessException("删除的通知公告数据为空");
        }

        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        boolean isRemoved = this.removeByIds(idList);
        if (isRemoved) {
            // 删除通知公告的同时，需要删除通知公告对应的用户通知状态
            userNoticeService.remove(new LambdaQueryWrapper<UserNotice>().in(UserNotice::getNoticeId, idList));
        }
        return isRemoved;
    }

    /**
     * 发布通知公告
     *
     * @param id 通知公告ID
     * @return 是否发布成功
     */
    @Override
    @Transactional
    public boolean publishNotice(Long id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            throw new BusinessException("通知公告不存在");
        }

        if (NoticePublishStatusEnum.PUBLISHED.getValue().equals(notice.getPublishStatus())) {
            throw new BusinessException("通知公告已发布");
        }

        Integer targetType = notice.getTargetType();
        String targetUserIds = notice.getTargetUserIds();
        if (NoticeTargetEnum.SPECIFIED.getValue().equals(targetType)
                && StrUtil.isBlank(targetUserIds)) {
            throw new BusinessException("推送指定用户不能为空");
        }

        notice.setPublishStatus(NoticePublishStatusEnum.PUBLISHED.getValue());
        notice.setPublisherId(SecurityUtils.getUserId());
        notice.setPublishTime(LocalDateTime.now());
        boolean publishResult = this.updateById(notice);

        if (publishResult) {
            // 发布通知公告的同时，删除该通告之前的用户通知数据，因为可能是重新发布
            userNoticeService.remove(
                    new LambdaQueryWrapper<UserNotice>().eq(UserNotice::getNoticeId, id)
            );

            // 添加新的用户通知数据
            List<String> targetUserIdList = null;
            if (NoticeTargetEnum.SPECIFIED.getValue().equals(targetType)) {
                targetUserIdList = Arrays.asList(targetUserIds.split(","));
            }

            List<User> targetUserList = userService.list(
                    new LambdaQueryWrapper<User>()
                            // 如果是指定用户，则筛选出指定用户
                            .in(
                                    NoticeTargetEnum.SPECIFIED.getValue().equals(targetType),
                                    User::getId,
                                    targetUserIdList
                            )
            );

            List<UserNotice> userNoticeList = targetUserList.stream().map(user -> {
                UserNotice userNotice = new UserNotice();
                userNotice.setNoticeId(id);
                userNotice.setUserId(user.getId());
                userNotice.setIsRead(0);
                return userNotice;
            }).toList();

            if (CollectionUtil.isNotEmpty(userNoticeList)) {
                userNoticeService.saveBatch(userNoticeList);
            }

            Set<String> receivers = targetUserList.stream().map(User::getUsername).collect(Collectors.toSet());

            Set<String> allOnlineUsers = userService.getAllOnlineUsers();

            // 找出在线用户的通知接收者
            Set<String> onlineReceivers = new HashSet<>(CollectionUtil.intersection(receivers, allOnlineUsers));

            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setId(id);
            noticeDTO.setTitle(notice.getTitle());
            noticeDTO.setType(notice.getType());
            noticeDTO.setPublishTime(notice.getPublishTime());

            onlineReceivers.forEach(receiver -> messagingTemplate.convertAndSendToUser(receiver, "/queue/message", noticeDTO));
        }
        return publishResult;
    }

    /**
     * 撤回通知公告
     *
     * @param id 通知公告ID
     * @return 是否撤回成功
     */
    @Override
    @Transactional
    public boolean revokeNotice(Long id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            throw new BusinessException("通知公告不存在");
        }

        if (!NoticePublishStatusEnum.PUBLISHED.getValue().equals(notice.getPublishStatus())) {
            throw new BusinessException("通知公告未发布或已撤回");
        }

        notice.setPublishStatus(NoticePublishStatusEnum.REVOKED.getValue());
        notice.setRevokeTime(LocalDateTime.now());
        notice.setUpdateBy(SecurityUtils.getUserId());

        boolean revokeResult = this.updateById(notice);

        if (revokeResult) {
            // 撤回通知公告的同时，需要删除通知公告对应的用户通知状态
            userNoticeService.remove(new LambdaQueryWrapper<UserNotice>()
                    .eq(UserNotice::getNoticeId, id)
            );
        }
        return revokeResult;
    }

    /**
     * 阅读获取通知公告详情
     *
     * @param id 通知公告ID
     * @return
     */
    @Override
    public NoticeDetailVO getNoticeDetail(Long id) {
        NoticeBO noticeBO = this.baseMapper.getNoticeDetail(id);
        // 更新用户通知公告的阅读状态
        Long userId = SecurityUtils.getUserId();
        userNoticeService.update(new LambdaUpdateWrapper<UserNotice>()
                .eq(UserNotice::getNoticeId, id)
                .eq(UserNotice::getUserId, userId)
                .eq(UserNotice::getIsRead, 0)
                .set(UserNotice::getIsRead, 1)
        );
        return noticeConverter.toDetailVO(noticeBO);
    }

    /**
     * 获取当前登录用户的通知公告列表
     *
     * @param queryParams 查询参数
     * @return 通知公告分页列表
     */
    @Override
    public IPage<UserNoticePageVO> getMyNoticePage(NoticePageQuery queryParams) {
        queryParams.setUserId(SecurityUtils.getUserId());
        return userNoticeService.getMyNoticePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
    }

}

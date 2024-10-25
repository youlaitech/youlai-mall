package com.youlai.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.system.model.form.NoticeForm;
import com.youlai.system.model.query.NoticePageQuery;
import com.youlai.system.model.vo.NoticeDetailVO;
import com.youlai.system.model.vo.NoticePageVO;
import com.youlai.system.model.vo.UserNoticePageVO;
import com.youlai.system.service.NoticeService;
import com.youlai.system.service.UserNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 通知公告前端控制层
 *
 * @author youlaitech
 * @since 2024-08-27 10:31
 */
@Tag(name = "通知公告接口")
@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    private final UserNoticeService userNoticeService;

    @Operation(summary = "通知公告分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('sys:notice:query')")
    public PageResult<NoticePageVO> getNoticePage(NoticePageQuery queryParams) {
        IPage<NoticePageVO> result = noticeService.getNoticePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增通知公告")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:notice:add')")
    public Result<?> saveNotice(@RequestBody @Valid NoticeForm formData) {
        boolean result = noticeService.saveNotice(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取通知公告表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('sys:notice:edit')")
    public Result<NoticeForm> getNoticeForm(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        NoticeForm formData = noticeService.getNoticeFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "阅读获取通知公告详情")
    @GetMapping("/{id}/detail")
    public Result<NoticeDetailVO> getNoticeDetail(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        NoticeDetailVO detailVO = noticeService.getNoticeDetail(id);
        return Result.success(detailVO);
    }

    @Operation(summary = "修改通知公告")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:notice:edit')")
    public Result<Void> updateNotice(
            @Parameter(description = "通知公告ID") @PathVariable Long id,
            @RequestBody @Validated NoticeForm formData
    ) {
        boolean result = noticeService.updateNotice(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "发布通知公告")
    @PatchMapping(value = "/{id}/publish")
    @PreAuthorize("@ss.hasPerm('sys:notice:publish')")
    public Result<Void> publishNotice(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        boolean result = noticeService.publishNotice(id);
        return Result.judge(result);
    }

    @Operation(summary = "撤回通知公告")
    @PatchMapping(value = "/{id}/revoke")
    @PreAuthorize("@ss.hasPerm('sys:notice:revoke')")
    public Result<Void> revokeNotice(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        boolean result = noticeService.revokeNotice(id);
        return Result.judge(result);
    }

    @Operation(summary = "删除通知公告")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:notice:delete')")
    public Result<Void> deleteNotices(
            @Parameter(description = "通知公告ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = noticeService.deleteNotices(ids);
        return Result.judge(result);
    }

    @Operation(summary = "全部已读")
    @PutMapping("/read-all")
    public Result<Void> readAll() {
        userNoticeService.readAll();
        return Result.success();
    }

    @Operation(summary = "获取我的通知公告分页列表")
    @GetMapping("/my-page")
    public PageResult<UserNoticePageVO> getMyNoticePage(
            NoticePageQuery queryParams
    ) {
        IPage<UserNoticePageVO> result = noticeService.getMyNoticePage(queryParams);
        return PageResult.success(result);
    }
}

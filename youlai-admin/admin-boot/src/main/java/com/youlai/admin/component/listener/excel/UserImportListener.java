package com.youlai.admin.component.listener.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.youlai.admin.pojo.form.UserImportForm;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/10 20:49
 */
@Component
@Scope("prototype")
public class UserImportListener extends AnalysisEventListener<UserImportForm.UserItem> {


    @Override
    public void invoke(UserImportForm.UserItem userItem, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

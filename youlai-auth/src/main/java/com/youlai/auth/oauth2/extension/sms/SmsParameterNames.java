/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.youlai.auth.oauth2.extension.sms;

/**
 * 短信验证码模式参数名称常量
 *
 * @author haoxr
 * @since 3.0.0
 */
public final class SmsParameterNames {

    /**
     * 手机号
     */
    public static final String MOBILE = "mobile";

    /**
     * 验证码
     */
    public static final String VERIFY_CODE = "verifyCode";


    private SmsParameterNames() {
    }

}

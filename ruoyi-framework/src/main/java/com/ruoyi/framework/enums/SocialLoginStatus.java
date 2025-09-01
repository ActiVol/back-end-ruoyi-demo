package com.ruoyi.framework.enums;

public enum SocialLoginStatus {
    //补充用户资料
    UPDATE_PROFILE(10001),
    //已有账户绑定该账户
    ACCOUNT_BOUND(10002),

    FAIL(10003),

    //成功
    SUCCESS( 200);
    private final int code;

    // 构造方法
    SocialLoginStatus(int code) {
        this.code = code;
    }

    // 获取状态代码
    public int getCode() {
        return code;
    }
}

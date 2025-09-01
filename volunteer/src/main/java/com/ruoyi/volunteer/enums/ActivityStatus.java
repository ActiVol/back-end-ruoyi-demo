package com.ruoyi.volunteer.enums;

public enum ActivityStatus {

    // 待确认、已确认、拒绝、已取消
    WAIT_CONFIRM(1, "待确认"),
    REFUSE(2, "拒绝"),
    CONFIRM(3, "已确认"),
    CLOSE(4, "已取消");

    private Integer key;
    private String desc;

    ActivityStatus(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static ActivityStatus getByKey(Integer key) {
        for (ActivityStatus e : values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        throw new RuntimeException("enum not exists.");
    }

}

package com.edu.zzti.common.enums;

/**
 * 在线状态
 */
public enum OnlineStatusEnums {

    /**
     * 在线
     */
    ONLINE(1,"在线"),

    /**
     * 在线
     */
    OFFLINE(0,"离线");


    private Integer value;

    private String name;

    OnlineStatusEnums(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getStringValue() {
        return String.valueOf(value);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static OnlineStatusEnums ofValue(Integer value){
        for (OnlineStatusEnums onlineStatusEnums : OnlineStatusEnums.values()) {
            if(onlineStatusEnums.getValue().equals(value)){
                return onlineStatusEnums;
            }
        }
        return null;
    }
}

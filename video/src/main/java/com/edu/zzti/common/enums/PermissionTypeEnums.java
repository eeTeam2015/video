package com.edu.zzti.common.enums;

/**
 * 权限类型
 */
public enum PermissionTypeEnums {

    /**
     * 菜单
     */
    MENU(1,"菜单"),

    /**
     * 接口
     */
    INTERFACE(2,"接口");

    private Integer value;

    private String name;

    PermissionTypeEnums(Integer value, String name) {
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

    public static PermissionTypeEnums ofValue(String value){
        for (PermissionTypeEnums operateStatusEnums : PermissionTypeEnums.values()) {
            if(operateStatusEnums.getStringValue().equals(value)){
                return operateStatusEnums;
            }
        }
        return null;
    }
}

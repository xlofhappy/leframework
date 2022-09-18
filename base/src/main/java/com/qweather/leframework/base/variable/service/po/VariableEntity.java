package com.qweather.leframework.base.variable.service.po;


import com.qweather.leframework.model.po.LePo;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class VariableEntity extends LePo {
    /**
     * 代码
     */
    private String code;
    /**
     * 值
     */
    private String value;

    public VariableEntity() {
        super();
    }

    public VariableEntity(Long id) {
        super(id);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
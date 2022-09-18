package com.qweather.leframework.base.dictionary.service.po;


import com.qweather.leframework.base.dictionary.service.cmd.ListDictionaryByPidCmd;
import com.qweather.leframework.model.mybatis.dao.BaseDao;
import com.qweather.leframework.model.po.LePo;

import java.util.Collections;
import java.util.List;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class DictionaryEntity extends LePo {

    /**
     * Tree ID
     */
    private Long pid;
    /**
     * i18n code
     */
    private String code;
    /**
     * 值
     */
    private String value;

    private List<DictionaryEntity> closestChildren;

    public DictionaryEntity() {
        super();
    }

    public DictionaryEntity(Long id) {
        super(id);
    }

    public List<DictionaryEntity> getClosestChildren() {
        if ( closestChildren == null ) {
            synchronized (this) {
                if ( closestChildren == null ) {
                    List<DictionaryEntity> dictionaryEntities = BaseDao.getInstance().execute(new ListDictionaryByPidCmd(this.getId()));
                    if ( dictionaryEntities != null ) {
                        closestChildren = dictionaryEntities;
                    } else {
                        closestChildren = Collections.emptyList();
                    }
                }
            }
        }
        return closestChildren;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
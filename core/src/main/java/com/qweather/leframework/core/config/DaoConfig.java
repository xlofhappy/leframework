package com.qweather.leframework.core.config;

import com.qweather.leframework.core.interfaces.IdGenerator;
import com.qweather.leframework.core.interfaces.defaults.DefaultIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoConfig {

    private IdGenerator idPool;

    @Autowired(required = false)
    public void setIdPool(IdGenerator idPool) {
        if ( this.idPool == null ) {
            this.idPool = idPool;
        }
    }

    public IdGenerator getIdPool() {
        if ( idPool == null ) {
            synchronized (DaoConfig.class) {
                if ( idPool == null ) {
                    idPool = new DefaultIdGenerator();
                }
            }
        }
        return idPool;
    }
}

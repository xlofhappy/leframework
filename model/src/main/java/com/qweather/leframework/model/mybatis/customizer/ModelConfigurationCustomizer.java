package com.qweather.leframework.model.mybatis.customizer;

import org.apache.ibatis.session.Configuration;
import com.qweather.leframework.model.ModelConstant;
import com.qweather.leframework.model.common.properties.ModelProperties;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xiaole
 */
@Component
public class ModelConfigurationCustomizer implements ConfigurationCustomizer {

    private final DataSource dataSource;
    private final ModelProperties modelProperties;

    @Autowired
    public ModelConfigurationCustomizer(@Qualifier("dataSource") DataSource dataSource, ModelProperties modelProperties) {
        this.dataSource = dataSource;
        this.modelProperties = modelProperties;
    }

    @Override
    public void customize(Configuration configuration) {
        try {
            //add page query support
            Connection connection = dataSource.getConnection();
            String databaseType = connection.getMetaData().getDatabaseProductName().toLowerCase();
            connection.close();
            Properties variables = configuration.getVariables();

            String data = variables.getProperty("baseTablePrefix");
            if (data == null) {
                variables.put("baseTablePrefix", modelProperties.getBaseTablePrefix());
            }
            data = variables.getProperty("limitBefore");
            if (data == null) {
                variables.put("limitBefore", ModelConstant.DATABASE_LIMIT_BEFORE.get(databaseType));
            }
            data = variables.getProperty("limitBetween");
            if (data == null) {
                variables.put("limitBetween", ModelConstant.DATABASE_LIMIT_BETWEEN.get(databaseType));
            }
            data = variables.getProperty("limitAfter");
            if (data == null) {
                variables.put("limitAfter", ModelConstant.DATABASE_LIMIT_AFTER.get(databaseType));
            }
            data = variables.getProperty("orderBy");
            if (data == null) {
                variables.put("orderBy", ModelConstant.DATABASE_ORDER_BY.get(databaseType));
            }
            configuration.setVariables(variables);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

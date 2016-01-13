package com.boxedfolder.carrot.config.general;

import com.boxedfolder.carrot.config.Profiles;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Profile(Profiles.CLOUD)
public class CloudConfig extends AbstractCloudConfig {
    @Inject
    protected Environment env;

    @Bean
    public ApplicationInstanceInfo applicationInfo() {
        return cloud().getApplicationInstanceInfo();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        try {
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://" + dbUri().getHost() + ":" + dbUri().getPort() + dbUri().getPath());
            dataSource.setUsername(dbUri().getUserInfo().split(":")[0]);
            dataSource.setPassword(dbUri().getUserInfo().split(":")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    private URI dbUri() throws Exception {
        return new URI(env.getProperty("DATABASE_URL"));
    }
}
package study.datajpa.config;

import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;
import study.datajpa.config.p6spy.CustomP6SpySqlFormat;

import javax.annotation.PostConstruct;

@Configuration
public class JpaConfig {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomP6SpySqlFormat.class.getName());
    }
}

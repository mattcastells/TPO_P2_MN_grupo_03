package uade.api.tpo_p2_mn_grupo_03.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    
    @Value("${api.host}")
    private String apiHost;

    public String getApiHost() {
        return apiHost;
    }
} 
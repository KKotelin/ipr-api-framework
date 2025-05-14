package config;

import lombok.Data;

@Data
public class ApiConfig {
    private String baseUrl;
    private Long timeout;
    private String username;
    private String password;
}

package config;

import lombok.Data;

@Data
public class UiConfig {
    private String baseUrl;
    private Long timeout;
    private String username;
    private String password;
    private String browser;
    private Boolean headless;
}

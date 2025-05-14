package config;

import lombok.Data;

@Data
public class EnvConfigDto {
    private ApiConfig apiConfig;
    private UiConfig uiConfig;
}

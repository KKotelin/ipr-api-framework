package config;

import lombok.Data;

@Data
public class DbConfigDto {
    private String url;
    private String username;
    private String password;
}

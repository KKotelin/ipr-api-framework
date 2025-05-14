package config;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.Data;

import java.io.InputStream;

@Data
public class Config {
    private EnvConfigDto env;
    private DbConfigDto db;

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            try (InputStream is = Config.class.getClassLoader().getResourceAsStream("application.yml")) {
                if (is == null) {
                    throw new RuntimeException("application.yml not found in resources");
                }
                YAMLMapper mapper = new YAMLMapper();
                instance = mapper.readValue(is, Config.class);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't load configuration: " + e.getMessage(), e);
            }
        }
        return instance;
    }
}

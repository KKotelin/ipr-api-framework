package utils;

import java.util.Map;

public class EngineTypeUtils {

    private static final Map<String, Integer> ENGINE_TYPE_MAP = Map.of(
            "Gasoline", 1,
            "Diesel", 2,
            "CNG", 3,
            "Hydrogenic", 4,
            "Electric", 5,
            "PHEV", 6

    );

    private static final Map<Integer, String> ID_TO_NAME_MAP = ENGINE_TYPE_MAP.entrySet()
            .stream()
            .collect(java.util.stream.Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public static Integer getIdByName(String name) {
        return ENGINE_TYPE_MAP.get(name);
    }

    public static String getNameById(Integer id) {
        return ID_TO_NAME_MAP.get(id);
    }
}

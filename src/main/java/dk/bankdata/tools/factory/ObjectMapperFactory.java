package dk.bankdata.tools.factory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperFactory {
    private static ObjectMapper objectMapper;

    private ObjectMapperFactory() {}

    public static ObjectMapper getInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        }

        return objectMapper;
    }
}

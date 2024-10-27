package com.giraone.templating;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giraone.templating.util.ObjectMapperBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SpelConverterTest {

    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperBuilder.build();
    private static final TypeReference<Map<String, Object>> MAP = new TypeReference<>() {
    };

    private static SecretKey SECRET_KEY;

    @Test
    void convert_simple_usingMap() {
        // act
        String result = SpelConverter.convert("Hey ${data.name}", Map.of("name", "John"));
        // assert
        assertThat(result).isEqualTo("Hey John");
    }

    @Test
    void convert_simple_usingJson() throws JsonProcessingException {
        // act
        String result = SpelConverter.convert("Hey ${data.name}","{\"name\":\"John\"}", OBJECT_MAPPER);
        // assert
        assertThat(result).isEqualTo("Hey John");
    }

    @Test
    @Disabled("Not yet implemented")
    void convert_simple_usingJsonPointer() throws JsonProcessingException {
        // act
        String result = SpelConverter.convert("Hey ${/data/name}","{\"name\":\"John\"}", OBJECT_MAPPER);
        // assert
        assertThat(result).isEqualTo("Hey John");
    }

    @ParameterizedTest
    @CsvSource({
        "/testdata/template-01.txt,/testdata/data-01.json,/testdata/expected-01.txt",
    })
    void convert_multiple(String filePathTemplate, String filePathData, String filePathExpected) throws Exception {

        // arrange
        File fileTemplate = new File("src/test/resources" + filePathTemplate);
        assertThat(fileTemplate.exists()).isTrue();
        String input = Files.readString(fileTemplate.toPath());

        File fileData = new File("src/test/resources" + filePathData);
        assertThat(fileData.exists()).isTrue();
        Map<String,Object> jsonData =OBJECT_MAPPER.readValue(fileData, MAP);

        File fileExpected = new File("src/test/resources" + filePathExpected);
        assertThat(fileExpected.exists()).isTrue();
        String output = Files.readString(fileExpected.toPath());

        // act
        String result = SpelConverter.convert(input, jsonData);
        // assert
        assertThat(result).isEqualTo(output);
    }
}
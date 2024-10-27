package com.giraone.templating;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpelConverter {

    private static final TypeReference<Map<String, Object>> MAP = new TypeReference<>() {
    };

    // Look for ${x}, where x is no '}'
    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^\\}]+)}");

    public static String convert(String template, String jsonData, ObjectMapper objectMapper) throws JsonProcessingException {
        return convert(template, objectMapper.readValue(jsonData, MAP));
    }

    public static String convert(String template, Map<String,Object> data) {

        Matcher matcher = PATTERN.matcher(template);
        StringBuilder sb = new StringBuilder();

        final SpelExpressionParser parser = new SpelExpressionParser();
        // put data map with name "data" into the context (root object)
        final StandardEvaluationContext evaluationContext = new StandardEvaluationContext(Map.of("data", data));
        // and tell SpEL, that this is no "Java bean", but a "map"
        evaluationContext.addPropertyAccessor(new MapAccessor());

        while (matcher.find()) {
            final String expressionString = matcher.group(1); // Extract the content inside ${}
            final Expression expression = parser.parseExpression(expressionString);
            final Object evaluatedExpression = expression.getValue(evaluationContext);
            final String evaluatedExpressionString = evaluatedExpression != null ? evaluatedExpression.toString() : "";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(evaluatedExpressionString));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
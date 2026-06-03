package com.aicodereviewer.analyzer;

public class AnalyzerFactory {

    public static CodeAnalyzer getAnalyzer(
            String type) {

        return switch(type.toLowerCase()) {

            case "complexity" ->
                    new ComplexityAnalyzer();

            case "naming" ->
                    new NamingAnalyzer();

            case "duplicate" ->
                    new DuplicateCodeAnalyzer();

            case "longmethod" ->
                    new LongMethodAnalyzer();

            case "documentation" ->
                    new DocumentationAnalyzer();

            default ->
                    throw new IllegalArgumentException(
                            "Invalid analyzer type"
                    );
        };
    }
}
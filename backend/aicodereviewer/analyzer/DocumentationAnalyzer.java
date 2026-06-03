package com.aicodereviewer.analyzer;

public class DocumentationAnalyzer
        implements CodeAnalyzer {

    @Override
    public String analyze(String code) {

        int comments = 0;

        String[] lines =
                code.split("\n");

        for(String line : lines) {

            String trimmed =
                    line.trim();

            if(trimmed.startsWith("//")
                    || trimmed.startsWith("/*")
                    || trimmed.startsWith("*")) {

                comments++;
            }
        }

        if(comments == 0) {

            return """
                   No documentation found.
                   Add comments for maintainability.
                   """;
        }

        return "Documentation Present";
    }
}
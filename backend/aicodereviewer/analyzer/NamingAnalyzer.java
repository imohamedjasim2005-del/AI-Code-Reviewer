package com.aicodereviewer.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamingAnalyzer implements CodeAnalyzer {

    @Override
    public String analyze(String code) {

        StringBuilder issues =
                new StringBuilder();

        Pattern pattern =
                Pattern.compile(
                        "class\\s+([A-Za-z_][A-Za-z0-9_]*)"
                );

        Matcher matcher =
                pattern.matcher(code);

        while (matcher.find()) {

            String className =
                    matcher.group(1);

            if (!Character.isUpperCase(
                    className.charAt(0))) {

                issues.append(
                        "Class ")
                        .append(className)
                        .append(
                                " should start with uppercase.\n"
                        );
            }
        }

        return issues.toString();
    }
}
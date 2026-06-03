package com.aicodereviewer.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongMethodAnalyzer
        implements CodeAnalyzer {

    private static final int LIMIT = 30;

    @Override
    public String analyze(String code) {

        Pattern pattern =
                Pattern.compile(
                        "\\{([^}]*)\\}"
                );

        Matcher matcher =
                pattern.matcher(code);

        StringBuilder report =
                new StringBuilder();

        int methodCounter = 1;

        while(matcher.find()) {

            String body =
                    matcher.group(1);

            int lines =
                    body.split("\n").length;

            if(lines > LIMIT) {

                report.append(
                        "Method ")
                        .append(methodCounter)
                        .append(
                                " exceeds ")
                        .append(LIMIT)
                        .append(
                                " lines.\n");
            }

            methodCounter++;
        }

        return report.toString();
    }
}
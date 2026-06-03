package com.aicodereviewer.analyzer;

public class ComplexityAnalyzer implements CodeAnalyzer {

    @Override
    public String analyze(String code) {

        int score = 0;

        score += count(code, "if");
        score += count(code, "for");
        score += count(code, "while");
        score += count(code, "switch");
        score += count(code, "catch");

        return String.valueOf(score);
    }

    private int count(String code, String keyword) {

        int occurrences = 0;
        int index = 0;

        while ((index = code.indexOf(keyword, index)) != -1) {
            occurrences++;
            index += keyword.length();
        }

        return occurrences;
    }
}
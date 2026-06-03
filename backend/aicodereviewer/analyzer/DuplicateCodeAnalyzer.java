package com.aicodereviewer.analyzer;

import java.util.HashSet;
import java.util.Set;

public class DuplicateCodeAnalyzer
        implements CodeAnalyzer {

    @Override
    public String analyze(String code) {

        String[] lines =
                code.split("\n");

        Set<String> uniqueLines =
                new HashSet<>();

        int duplicates = 0;

        for(String line : lines) {

            String trimmed =
                    line.trim();

            if(trimmed.isEmpty()) {
                continue;
            }

            if(!uniqueLines.add(trimmed)) {
                duplicates++;
            }
        }

        return "Duplicate Lines: "
                + duplicates;
    }
}
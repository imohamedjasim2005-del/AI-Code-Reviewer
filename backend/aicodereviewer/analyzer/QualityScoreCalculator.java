package com.aicodereviewer.analyzer;

public class QualityScoreCalculator {

    public int calculate(
            int complexity,
            int duplicateLines,
            boolean namingIssue,
            boolean longMethodIssue) {

        int score = 100;

        score -= complexity * 2;

        score -= duplicateLines * 2;

        if(namingIssue) {
            score -= 10;
        }

        if(longMethodIssue) {
            score -= 15;
        }

        return Math.max(score, 0);
    }
}
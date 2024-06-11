package com.example.sample.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;

public class RegexTests extends EvaluatedTimeTests {

    @Test
    public void regexMatchTest() {
        String input = "<iframe src=\"javascript:alert(parent.document.domain)\">";
        String regex = "<(\\/?)script([^>]*)>|<(\\/?)object([^>]*)>|<(\\/?)applet([^>]*)>|<(\\/?)embed([^>]*)>|<(\\/?)form([^>]*)>|<(\\/?)iframe([^>]*)>";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // matches() try to match pattern against entire input string
        if (matcher.matches()) {
            System.out.println("matched: " + matcher.group());
        } else {
            System.out.println("NOT matched");
        }
    }

    @Test
    public void regexFindTest() {
        String input = "xxxxxx<iframe src=\"javascript:alert(parent.document.domain)\">2222222";
        String regex = "<(\\/?)script([^>]*)>|<(\\/?)object([^>]*)>|<(\\/?)applet([^>]*)>|<(\\/?)embed([^>]*)>|<(\\/?)form([^>]*)>|<(\\/?)iframe([^>]*)>";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // find() try to match pattern against subsequence of input string
        if (matcher.find()) {
            System.out.println("found: " + matcher.group());
        } else {
            System.out.println("NOT found");
        }
    }
}

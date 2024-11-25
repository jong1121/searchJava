package com.example.searchjava;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchJava {
    public static void main(String[] args) {
        System.out.println("Start!");
        String projectRootPath = "C:\\Users\\jong_\\IdeaProjects\\springBoot2718Temp\\src\\main\\java\\com\\example\\springboot2718temp";
        try {
            Files.walk(Paths.get(projectRootPath))
                    .filter(filePath -> filePath.toString().endsWith(".java"))
                    .forEach(filePath -> searchInFile(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("End!");
    }

    private static void searchInFile(Path filePath) {
        // 찾을 txCode 값
        String searchString = "AAABBC";
        // txCode의 변수명 찾기 패턴
        Pattern variablePattern = Pattern.compile("private\\s+final\\s+String\\s+(\\w+)\\s*=\\s*\"" + searchString + "\";");
        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            byte[] data = new byte[(int) filePath.toFile().length()];
            fis.read(data);
            String content = new String(data);
            Matcher variableMatcher = variablePattern.matcher(content);
            if (variableMatcher.find()) {
                String variableName = variableMatcher.group(1); // 첫 번째 그룹에서 변수명 추출
                System.out.println("txCode : " + searchString);
                System.out.println("Found variable : " + variableName);
                System.out.println("in file : " + filePath);
                Pattern equalsPattern = Pattern.compile("\\b" + variableName + "\\b\\.equals\\s*\\(([^)]+)\\)|\\.equals\\s*\\(" + variableName + "\\b");
                Matcher equalsMatcher = equalsPattern.matcher(content);
                if (equalsMatcher.find()) {
                    int checkIndex = equalsMatcher.end();
                    int startIndex = 0;
                    int endIndex = 0;
                    while (checkIndex < content.length() && endIndex == 0 ) {
                        char currentChar = content.charAt(checkIndex);
                        if (currentChar == '{') {
                            startIndex = checkIndex;
                        } else if (currentChar == '}') {
                            endIndex = checkIndex;
                        }
                        checkIndex++;
                    }
                    // 코드 블록 출력
                    if (endIndex != 0) {
                        String codeBlock = content.substring(startIndex, endIndex + 1);
                        System.out.println("Found code block : " + codeBlock);

                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

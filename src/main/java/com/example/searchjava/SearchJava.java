package com.example.searchjava;

import com.example.searchjava.dto.ApiDTO;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SearchJava {
    public static final String projectRootPath = "C:\\Users\\jong_\\IdeaProjects\\springBoot2718Temp\\src\\main\\java";
    public static void main(String[] args){
        System.out.println("Start!");
        rotateSources(projectRootPath);
        System.out.println("End!");
    }
    private static void rotateSources(String projectRootPath) {
        try(Stream<Path> paths =  Files.walk(Paths.get(projectRootPath))){
            paths.filter(filePath -> filePath.toString().endsWith(".java"))
                .forEach(SearchJava::chaseApi);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void chaseApi(Path filePath) {
        ApiDTO apiDTO = new ApiDTO();
        String sourceContent;
        try {
            sourceContent = getSourceContent(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        apiDTO.setTxCode("AAABBC");
        apiDTO = scanTxCode(sourceContent, apiDTO);
        if( apiDTO == null ) {
            return;
        }
        apiDTO.setFindFilePath(filePath.toString());
        apiDTO = scanTxCodeBlock(sourceContent, apiDTO);
        if( apiDTO == null ){
            return;
        }
        apiDTO = getServiceNameAndMethod(apiDTO);
        if( apiDTO == null ){
            return;
        }
        apiDTO = getServiceClass(sourceContent, apiDTO);
        if( apiDTO == null){
            return;
        }
        apiDTO = getServiceJavaPath(sourceContent, apiDTO);
        if( apiDTO == null){
            return;
        }

        filePath = Paths.get(apiDTO.getServiceJavaPath());
        try {
            sourceContent = getSourceContent(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        apiDTO = getServiceMethodBlock(sourceContent, apiDTO);



        System.out.println(apiDTO);

    }



    private static String getSourceContent(Path filePath)  {
        try(FileInputStream fis = new FileInputStream(filePath.toFile())){
            byte[] data = new byte[(int) filePath.toFile().length()];
            fis.read(data);
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static ApiDTO scanTxCode (String sourceContent, ApiDTO apiDTO) {
        Pattern txCodeVariablePattern = Pattern.compile("private\\s+final\\s+String\\s+(\\w+)\\s*=\\s*\"" + apiDTO.getTxCode() + "\";");
        Matcher txCodeVariableMatcher = txCodeVariablePattern.matcher(sourceContent);
        if (!txCodeVariableMatcher.find()){
            return null;
        }
        String txCodeVariable = txCodeVariableMatcher.group(1);
        apiDTO.setTxCodeVariable(txCodeVariable);
        return apiDTO;
    }
    private static ApiDTO scanTxCodeBlock (String sourceContent, ApiDTO apiDTO) {
        Pattern equalsPattern = Pattern.compile("\\b" + apiDTO.getTxCodeVariable() + "\\b\\.equals\\s*\\(([^)]+)\\)|\\.equals\\s*\\(" + apiDTO.getTxCodeVariable() + "\\b");
        Matcher equalsMatcher = equalsPattern.matcher(sourceContent);
        if (!equalsMatcher.find()) {
            return null;
        }
        int checkIndex = equalsMatcher.end();
        int startIndex = 0;
        int endIndex = 0;
        while (endIndex == 0 && checkIndex < sourceContent.length()) {
            char currentChar = sourceContent.charAt(checkIndex);
            if (currentChar == '{') {
                startIndex = checkIndex;
            } else if (currentChar == '}') {
                endIndex = checkIndex;
            }
            checkIndex++;
        }
        if (endIndex == 0){
            return null;
        }
        String txCodeBlock = sourceContent.substring(startIndex, endIndex + 1);
        apiDTO.setTxCodeBlock(txCodeBlock);
        return apiDTO;
    }
    private static ApiDTO getServiceNameAndMethod (ApiDTO apiDTO) {
        Pattern servicePattern = Pattern.compile("(\\b\\w+Service)\\.(\\w+)\\(");
        Matcher serviceMatcher = servicePattern.matcher(apiDTO.getTxCodeBlock());
        if (!serviceMatcher.find()) {
            return null;
        }
        String serviceName = serviceMatcher.group(1);
        String serviceMethodName = serviceMatcher.group(2);
        apiDTO.setServiceName(serviceName);
        apiDTO.setServiceMethodName(serviceMethodName);
        return apiDTO;
    }
    private static ApiDTO getServiceClass(String sourceContent, ApiDTO apiDTO) {
        Pattern serviceClassPattern = Pattern.compile("private\\s+final\\s+(\\b\\w+\\b)\\s+"+apiDTO.getServiceName()+"\\s*;");
        Matcher serviceClassMatcher = serviceClassPattern.matcher(sourceContent);
        if (!serviceClassMatcher.find()) {
            return null;
        }
        String serviceClass = serviceClassMatcher.group(1);
        apiDTO.setServiceClass(serviceClass);
        return apiDTO;
    }
    private static ApiDTO getServiceJavaPath(String sourceContent, ApiDTO apiDTO) {
        Pattern importServiceClassPattern = Pattern.compile("import\\s+(.*"+apiDTO.getServiceClass()+");");
        Matcher importServiceClassMatcher = importServiceClassPattern.matcher(sourceContent);
        if (!importServiceClassMatcher.find()){
            return null;
        }
        String importServiceClassPath = importServiceClassMatcher.group(1);
        importServiceClassPath = importServiceClassPath.replace(".","\\");
        importServiceClassPath = importServiceClassPath+".java";
        importServiceClassPath = projectRootPath +"\\" + importServiceClassPath;
        apiDTO.setServiceJavaPath(importServiceClassPath);
        return apiDTO;
    }
    private static ApiDTO getServiceMethodBlock(String sourceContent, ApiDTO apiDTO) {
        String serviceMethodName = apiDTO.getServiceMethodName();
        Pattern serviceMethodPattern = Pattern.compile("public\\s+.*\\s+"+serviceMethodName+"\\(");
        Matcher serviceMethodMatcher = serviceMethodPattern.matcher(sourceContent);
        if (!serviceMethodMatcher.find()){
            return null;
        }
        int checkIndex = serviceMethodMatcher.end();
        int startIndex = 0;
        int endIndex = 0;
        while (endIndex == 0 && checkIndex < sourceContent.length()) {
            char currentChar = sourceContent.charAt(checkIndex);
            if (currentChar == '{') {
                startIndex = checkIndex;
            } else if (currentChar == '}'){
                endIndex = checkIndex;
            }
            checkIndex++;
        }
        if (endIndex == 0) {
            return null;
        }
        String serviceMethodBlock = sourceContent.substring(startIndex, endIndex + 1);
        apiDTO.setServiceMethodBlock(serviceMethodBlock);
        return apiDTO;
    }
}
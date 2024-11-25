package com.example.searchjava.dto;

public class ApiDTO {
    private String txCode;
    private String txCodeVariable;
    private String findFilePath;
    private String txCodeBlock;
    private String serviceName;
    private String serviceMethodName;
    private String serviceClass;
    private String serviceMethodBlock;

    @Override
    public String toString() {
        return "ApiDTO{" +
                "txCode='" + txCode + '\'' +
                ", txCodeVariable='" + txCodeVariable + '\'' +
                ", findFilePath='" + findFilePath + '\'' +
                ", txCodeBlock='" + txCodeBlock + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceMethodName='" + serviceMethodName + '\'' +
                ", serviceClass='" + serviceClass + '\'' +
                ", serviceMethodBlock='" + serviceMethodBlock + '\'' +
                ", serviceJavaPath='" + serviceJavaPath + '\'' +
                '}';
    }

    public String getServiceMethodBlock() {
        return serviceMethodBlock;
    }

    public void setServiceMethodBlock(String serviceMethodBlock) {
        this.serviceMethodBlock = serviceMethodBlock;
    }

    public String getServiceJavaPath() {
        return serviceJavaPath;
    }

    public void setServiceJavaPath(String serviceJavaPath) {
        this.serviceJavaPath = serviceJavaPath;
    }

    private String serviceJavaPath;

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceMethodName() {
        return serviceMethodName;
    }

    public void setServiceMethodName(String serviceMethodName) {
        this.serviceMethodName = serviceMethodName;
    }

    public String getTxCodeBlock() {
        return txCodeBlock;
    }

    public void setTxCodeBlock(String txCodeBlock) {
        this.txCodeBlock = txCodeBlock;
    }

    public String getFindFilePath() {
        return findFilePath;
    }

    public void setFindFilePath(String findFilePath) {
        this.findFilePath = findFilePath;
    }

    public String getTxCodeVariable() {
        return txCodeVariable;
    }

    public void setTxCodeVariable(String txCodeVariable) {
        this.txCodeVariable = txCodeVariable;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getTxCode() {
        return txCode;
    }
}
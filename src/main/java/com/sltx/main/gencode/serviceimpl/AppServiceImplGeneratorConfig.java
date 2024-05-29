package com.sltx.main.gencode.serviceimpl;


import io.jboot.config.annotation.PropertyConfig;

/**
 * api code generation configuration
 * @author Rlax
 *
 */
@PropertyConfig(prefix="jboot.admin.serviceimpl.ge")
public class AppServiceImplGeneratorConfig {


    private String modelpackage;

    private String servicepackage;

    private String serviceimplpackage;

    private String removedtablenameprefixes;

    private String excludedtable;

    private String excludedtableprefixes;

    public String getModelpackage() {
        return modelpackage;
    }

    public void setModelpackage(String modelpackage) {
        this.modelpackage = modelpackage;
    }

    public String getServicepackage() {
        return servicepackage;
    }

    public void setServicepackage(String servicepackage) {
        this.servicepackage = servicepackage;
    }

    public String getRemovedtablenameprefixes() {
        return removedtablenameprefixes;
    }

    public void setRemovedtablenameprefixes(String removedtablenameprefixes) {
        this.removedtablenameprefixes = removedtablenameprefixes;
    }

    public String getExcludedtable() {
        return excludedtable;
    }

    public void setExcludedtable(String excludedtable) {
        this.excludedtable = excludedtable;
    }

    public String getExcludedtableprefixes() {
        return excludedtableprefixes;
    }

    public void setExcludedtableprefixes(String excludedtableprefixes) {
        this.excludedtableprefixes = excludedtableprefixes;
    }

    public String getServiceimplpackage() {
        return serviceimplpackage;
    }

    public void setServiceimplpackage(String serviceimplpackage) {
        this.serviceimplpackage = serviceimplpackage;
    }

    @Override
    public String toString() {
        return "AppServiceImplGeneratorConfig{" +
                "modelpackage='" + modelpackage + '\'' +
                ", servicepackage='" + servicepackage + '\'' +
                ", serviceimplpackage='" + serviceimplpackage + '\'' +
                ", removedtablenameprefixes='" + removedtablenameprefixes + '\'' +
                ", excludedtable='" + excludedtable + '\'' +
                ", excludedtableprefixes='" + excludedtableprefixes + '\'' +
                '}';
    }
}

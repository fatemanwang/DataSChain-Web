package com.sltx.main.gencode.model;


import io.jboot.config.annotation.PropertyConfig;

/**
 * model Code generation configuration
 * @author Rlax
 *
 */
@PropertyConfig(prefix="jboot.admin.model.ge")
public class AppModelGeneratorConfig {


    private String modelpackage;

    private String removedtablenameprefixes;

    private String excludedtable;

    private String excludedtableprefixes;

    public String getModelpackage() {
        return modelpackage;
    }

    public void setModelpackage(String modelpackage) {
        this.modelpackage = modelpackage;
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

    @Override
    public String toString() {
        return "AppGeneratorConfig{" +
                "modelpackage='" + modelpackage + '\'' +
                ", removedtablenameprefixes='" + removedtablenameprefixes + '\'' +
                ", excludedtable='" + excludedtable + '\'' +
                ", excludedtableprefixes='" + excludedtableprefixes + '\'' +
                '}';
    }
}

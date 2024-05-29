package com.sltx.main.gencode.service;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;

import io.jboot.Jboot;

import com.sltx.main.gencode.model.AppMetaBuilder;

import io.jboot.codegen.CodeGenHelpler;
import io.jboot.codegen.service.JbootServiceInterfaceGenerator;

import javax.sql.DataSource;

import java.util.List;

/**
 * service generate
 * @author Rlax
 *
 */
public class AppServiceGenerator {

    public static void doGenerate() {
        AppServiceGeneratorConfig config = Jboot.config(AppServiceGeneratorConfig.class);

        System.out.println(config.toString());

        if (StrKit.isBlank(config.getModelpackage())) {
            System.err.println("jboot.admin.service.ge.modelpackage not nullable");
            System.exit(0);
        }

        if (StrKit.isBlank(config.getServicepackage())) {
            System.err.println("jboot.admin.service.ge.servicepackage not nullable");
            System.exit(0);
        }

        String modelPackage = config.getModelpackage();
        String servicepackage = config.getServicepackage();

        System.out.println("start generate...");
        System.out.println("generate dir:" + servicepackage);

        DataSource dataSource = CodeGenHelpler.getDatasource();

        AppMetaBuilder metaBuilder = new AppMetaBuilder(dataSource);

        if (StrKit.notBlank(config.getRemovedtablenameprefixes())) {
            metaBuilder.setRemovedTableNamePrefixes(config.getRemovedtablenameprefixes().split(","));
        }

        if (StrKit.notBlank(config.getExcludedtableprefixes())) {
            metaBuilder.setSkipPre(config.getExcludedtableprefixes().split(","));
        }

        List<TableMeta> tableMetaList = metaBuilder.build();
        CodeGenHelpler.excludeTables(tableMetaList, config.getExcludedtable());

        new JbootServiceInterfaceGenerator(servicepackage, modelPackage).generate(tableMetaList);

        System.out.println("service generate finished !!!");

    }
}

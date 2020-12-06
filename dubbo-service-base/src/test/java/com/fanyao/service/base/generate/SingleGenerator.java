package com.fanyao.service.base.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: bugProvider
 * @date: 2019/10/10 23:27
 * @description: 代码生成
 */
public class SingleGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("base.dir");
        String outPutDir = projectPath + "/dubbo-service-base/src/main/java";
        gc.setOutputDir(outPutDir);
        gc.setAuthor("bugProvider");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setFileOverride(true);
        gc.setIdType(IdType.UUID);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.fanyao.dubbo.base");
        pc.setEntity("domain");
        pc.setMapper("mapper");
//        pc.setService("service");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/ftl/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/dubbo-service-base/src/main/resources/mapper/" + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // ==================  自定义模板配置： 项目的 src/main/resources/templates 目录下  ======================
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null)                                                        // 设置生成xml的模板
                .setEntity("ftl/entity.java")                                // 设置生成entity的模板
                .setMapper("ftl/mapper.java")                                // 设置生成mapper的模板
//                .setController("ftl/controller.java")                       // 设置生成service的模板
                .setService("ftl/service.java")                            // 设置生成serviceImpl的模板
                .setServiceImpl("ftl/serviceImpl.java");                    // 设置生成controller的模板

        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.cebon.web.controller.BaseController");
        strategy.setInclude(scanner("表名"));
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 自动填充字段
        List<TableFill> tableFills = new ArrayList<>();
        TableFill tableFill1 = new TableFill("deleted", FieldFill.INSERT);
        TableFill tableFill2 = new TableFill("create_time", FieldFill.INSERT);
        TableFill tableFill3 = new TableFill("update_time", FieldFill.UPDATE);

        tableFills.add(tableFill1);
        tableFills.add(tableFill2);
        tableFills.add(tableFill3);
        strategy.setTableFillList(tableFills);

//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}

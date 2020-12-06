package com.fanyao.service.base.generate;

import com.baomidou.mybatisplus.annotation.DbType;
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
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.mysql.cj.jdbc.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: bugProvider
 * @date: 2019/10/11 00:43
 * @description:
 */
public class ModuleGenerator {

    static final String projectPath = System.getProperty("base.dir");

    public static void main(String[] args) {
        String[] tableNames = new String[]{"room"};
//        String[] modules = new String[]{"service"};//项目模块名，需自定义
        String[] modules = new String[]{"model","dao","service", "web"};//项目模块名，需自定义

        String dbName = scanner("数据库名");
        String dbUserName = scanner("数据库账号");
        String dbPassword = scanner("数据库密码");

        for (String module : modules) {
            moduleGenerator(dbName,dbUserName,dbPassword,module, tableNames);
        }
    }

    private static void moduleGenerator( String dbName,  String dbUserName, String dbPassword,String module, String[] tableNames) {

        GlobalConfig globalConfig = getGlobalConfig(module);// 全局配置

        DataSourceConfig dataSourceConfig = getDataSourceConfig(dbName,dbUserName,dbPassword);// 数据源配置

        PackageConfig packageConfig = getPackageConfig(module);// 包配置

        InjectionConfig injectionConfig = getInjectionConfig(module);// mapper.xml配置

        StrategyConfig strategyConfig = getStrategyConfig(tableNames);// 策略配置

        TemplateConfig templateConfig = getTemplateConfig(module);// 配置模板

        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig)
                .setCfg(injectionConfig)
                .setTemplate(templateConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();

    }

    private static InjectionConfig getInjectionConfig(String module) {

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                /*if (module.equals("web")) {
                    String url = scanner("模块名");
                    Map<String, Object> map = new HashMap<>();
                    map.put("url",url);
                    this.setMap(map);
                }*/
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/ftl/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/" + module + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        if (module.equals("dao")) {
            cfg.setFileOutConfigList(focList);
        }
        return cfg;
    }

    private static TemplateConfig getTemplateConfig(String module) {
        TemplateConfig templateConfig = new TemplateConfig();
        if ("model".equals(module)) {
            templateConfig.setEntity("templates/ftl/entity.java")
                    .setMapper(null)
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);
        } else if ("dao".equals(module)) {
            templateConfig.setEntity(null)
                    .setMapper("templates/ftl/mapper.java")
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);
        } else if ("service".equals(module)) {
            templateConfig.setEntity(null)
                    .setMapper(null)
                    .setXml(null)
                    .setService("templates/ftl/service.java")
                    .setServiceImpl("templates/ftl/serviceImpl.java")
                    .setController(null);
        } else if ("web".equals(module)) {//web模块只生成controller代码
            templateConfig.setEntity(null)
                    .setMapper(null)
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController("templates/ftl/controller.java");
        } else throw new IllegalArgumentException("参数匹配错误，请检查");
        return templateConfig;
    }

    private static StrategyConfig getStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();

        // 自动填充字段
        List<TableFill> tableFills = new ArrayList<>();
        TableFill tableFill1 = new TableFill("deleted", FieldFill.INSERT);
        TableFill tableFill2 = new TableFill("create_time", FieldFill.INSERT);
        TableFill tableFill3 = new TableFill("update_time", FieldFill.UPDATE);
        TableFill tableFill4 = new TableFill("status", FieldFill.INSERT);

        tableFills.add(tableFill1);
        tableFills.add(tableFill2);
        tableFills.add(tableFill3);
        tableFills.add(tableFill4);

        strategyConfig
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
//                .setSuperControllerClass("com.cebon.web.controller.BaseController")
                .setEntityTableFieldAnnotationEnable(true)
                .setTableFillList(tableFills)
                .setControllerMappingHyphenStyle(true)
                .setRestControllerStyle(true)
                .setLogicDeleteFieldName("deleted")
                .setInclude(tableNames);

        return strategyConfig;
    }

    private static PackageConfig getPackageConfig(String module) {
        PackageConfig packageConfig = new PackageConfig();
        String packageName = "com.cebon";//不同模块 代码生成具体路径自定义指定

        packageConfig.setParent(packageName)
                .setEntity("model.po")
                .setMapper("dao.mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("web.controller");

        return packageConfig;

    }

    private static DataSourceConfig getDataSourceConfig(String dbName,  String dbUserName, String dbPassword) {
        String dbUrl = "jdbc:mysql:///" + dbName + "?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName(Driver.class.getName())
                .setUsername(dbUserName)
                .setPassword(dbPassword)
                .setUrl(dbUrl);
        return dataSourceConfig;
    }

    private static GlobalConfig getGlobalConfig(String module) {
        GlobalConfig globalConfig = new GlobalConfig();
        String outPutDir = projectPath + "/" + module + "/src/main/java";
        globalConfig.setOpen(false)//new File(module).getAbsolutePath()得到模块根目录路径，因事Maven项目，代码指定路径自定义调整
                .setOutputDir(outPutDir)//生成文件的输出目录
                .setFileOverride(true)//是否覆盖已有文件
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setActiveRecord(false)
                .setAuthor("bugProvide")
                .setDateType(DateType.ONLY_DATE)
                .setIdType(IdType.UUID);
        return globalConfig;
    }

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

}

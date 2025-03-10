package jd.mlz.module;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2024-12-09
 */

public class CodeGeneratorW {

    //表名
    final static String TABLE_NAME = "user";
    final static String PROJECT_PATH = System.getProperty("user.dir");
    final static String MODULE = "module";
    final static String PACKAGE_NAME = "jd.mlz.module.module.user";
    final static String AUTHOR = "wangfeiyu";
    final static String DATABASE_URL = "jdbc:mysql://localhost:3306/lafeng?useSSL=false&serverTimezone=UTC";
    final static String DATABASE_USERNAME = "root";
    final static String DATABASE_PASSWORD = "wangfeiyu@";

    final static String TEMPLATE_PATH_ENTITY = "templates/entity.java";
//        final static String TEMPLATE_PATH_ENTITY = null;
    final static String TEMPLATE_PATH_MAPPER = "templates/mapper.java";
//        final static String TEMPLATE_PATH_MAPPER = null;
    final static String TEMPLATE_PATH_MAPPER_XML = "templates/mapper.xml.vm";
//    final static String TEMPLATE_PATH_MAPPER_XML = null;

    final static String TEMPLATE_PATH_SERVICE = null;
    final static String TEMPLATE_PATH_SERVICE_IMPL = null;
    final static String TEMPLATE_PATH_CONTROLLER = null;

    public static void main(String[] args) {
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(PROJECT_PATH + "/" + MODULE + "/src/main/java") // 设置输出目录
                .setAuthor(AUTHOR) // 设置作者
                .setOpen(false) // 设置生成后是否自动打开目录
                .setFileOverride(true) // 设置文件存在时是否覆盖
                .setMapperName("%sMapper")
                .setServiceName("%sService")
                .setControllerName("%sController");

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL) // 设置数据库类型
                .setUrl(DATABASE_URL) // 数据库连接URL
                .setUsername(DATABASE_USERNAME) // 数据库用户名
                .setPassword(DATABASE_PASSWORD) // 数据库密码
                .setDriverName("com.mysql.cj.jdbc.Driver") // 数据库驱动类名
                //自定义数据库表---实体类型转换
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig1, String fieldType) {
                        //tinyint转换成Boolean
                        if (fieldType.toLowerCase().contains("bigint")) {
                            return DbColumnType.BIG_INTEGER;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig1, fieldType);
                    }
                });
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude(TABLE_NAME) // 指定需要生成代码的表名
                .setNaming(NamingStrategy.underline_to_camel) // 设置表名转类名策略
                .setColumnNaming(NamingStrategy.underline_to_camel) // 设置列名转属性名策略
                .setEntityLombokModel(true)  // 设置实体类使用Lombok模型
                .setChainModel(true);
        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PACKAGE_NAME) // 设置父包名
                .setMapper("mapper") // 设置Mapper接口所在的子包名
                .setEntity("entity"); // 设置实体类所在的子包名


        // 自定义配置（在resources下生成mapper.xml）
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(TEMPLATE_PATH_MAPPER_XML) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return PROJECT_PATH + "/" + MODULE + "/src/main/resources/mybatis/mapper/" + NamingStrategy.underlineToCamel(tableInfo.getName())
                        + "/" + NamingStrategy.underlineToCamel(tableInfo.getName())  + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null) // 不在java包下生成XML文件
                .setController(TEMPLATE_PATH_CONTROLLER)
                .setService(TEMPLATE_PATH_SERVICE)
                .setServiceImpl(TEMPLATE_PATH_SERVICE_IMPL)
                .setEntity(TEMPLATE_PATH_ENTITY) // 设置实体类模板路径
                .setMapper(TEMPLATE_PATH_MAPPER); // 设置Mapper接口模板路径

        // 整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplate(templateConfig)
                .setCfg(cfg);

        // 执行生成
        autoGenerator.execute();
    }
}

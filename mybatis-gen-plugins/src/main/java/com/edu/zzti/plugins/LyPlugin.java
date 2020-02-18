package com.edu.zzti.plugins;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Mybatis generator 忽略Getter和Setter生成
 * @author sunkl
 * @date 2018/12/5
 */
public class LyPlugin extends PluginAdapter {

    private String modelClassName;
    private String modelClassPackageName;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        /**
         * 该代码表示在生成class的时候，
         * 向topLevelClass添加一个@Setter和@Getter注解
         */
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.AllArgsConstructor");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addImportedType("lombok.EqualsAndHashCode");
        topLevelClass.addImportedType("javax.persistence.Table");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");
        topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = false)");
        topLevelClass.addAnnotation("@Table(name = \""+introspectedTable.getFullyQualifiedTable()+"\")");
        topLevelClass.addJavaDocLine("/**");
        String remarks = introspectedTable.getRemarks();
        if (StringUtils.isNotBlank(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);
            }
        }
        topLevelClass.addJavaDocLine(" * @author "+System.getProperty("user.name"));
        topLevelClass.addJavaDocLine(" * @date "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addImportedType("com.ly.vrps.common.model.BaseModel");
        topLevelClass.setSuperClass("BaseModel");
        this.modelClassName = topLevelClass.getType().getShortName();
        this.modelClassPackageName = topLevelClass.getType().getFullyQualifiedName();
        return super.modelBaseRecordClassGenerated(topLevelClass,
                introspectedTable);
    }

    /**
     * 该方法在生成每一个属性的getter方法时候调用，
     * 如果我们不想生成getter，直接返回false即可
     * @author sunkl
     * @date 2018年12月5日19:16:28
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     * 该方法在生成每一个属性的setter方法时候调用，
     * 如果我们不想生成setter，直接返回false即可
     * @author sunkl
     * @date 2018年12月5日19:16:23
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * "+introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");
        boolean b = super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
        List<String> strings = Arrays.asList("id", "createBy", "createTime", "updateBy", "updateTime","remark");
        if(strings.contains(field.getName())){
            return false;
        }
        return b;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        interfaze.addJavaDocLine("/**");
        String remarks = introspectedTable.getRemarks();
        if (StringUtils.isNotBlank(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                interfaze.addJavaDocLine(" * " + remarkLine);
            }
        }
        interfaze.addJavaDocLine(" * @author "+System.getProperty("user.name"));
        interfaze.addJavaDocLine(" * @date "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        interfaze.addJavaDocLine(" */");
        interfaze.addImportedType(new FullyQualifiedJavaType("com.ly.vrps.common.dao.BaseDao"));
        interfaze.addImportedType(new FullyQualifiedJavaType(this.modelClassPackageName));
        interfaze.addSuperInterface(new FullyQualifiedJavaType("BaseDao<"+this.modelClassName+">"));
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

}

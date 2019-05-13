package com.dev.tools.kit.easycoding.gen.copybean.method.gen;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.CollectionListModel;

import java.util.List;

/**
 * @Description:构建器
 * @Author: zhangjianfeng
 * @Date: 2019-05-10
 */
public class MethodTextBuilder {


    private StringBuilder methodText = new StringBuilder();

    public MethodTextBuilder() {
    }

    /**
     *
     * 追加字符并换行
     *
     * @param str
     */
    public MethodTextBuilder appendln(String str) {
        return this.append(str).newLine();
    }

    /**
     * 换行
     */
    public MethodTextBuilder newLine() {
        return this.append("\n");
    }

    /**
     * 追加字符
     *
     * @param str
     */
    public MethodTextBuilder append(String str) {
        methodText.append(str);
        return this;
    }

    /**
     * 追加方法头
     *
     * @param psiMethod
     * @return
     */
    public MethodTextBuilder appendMethodHead(PsiMethod psiMethod) {
        methodText.append("private ")
                .append(psiMethod.getReturnType().getPresentableText())
                .append(" ")
                .append(psiMethod.getName())
                .append("(")
                .append(psiMethod.getParameterList().getParameters()[0].getType().getPresentableText())
                .append(" source){");
        return this;
    }

    /**
     * 生成对象属性拷贝
     * @param project
     * @param target
     * @return
     */
    public MethodTextBuilder appendMethodBody4Obj(Project project, PsiType target) {
        String targeClassName = target.getPresentableText();
        String targeVarName = "target";
        String sourceVarName = "source";
        JavaPsiFacade facade = JavaPsiFacade.getInstance(project);
        PsiClass targetClass = facade.findClass(target.getCanonicalText(), GlobalSearchScope.allScope(project));
        List<PsiField> targetFields = new CollectionListModel<>(targetClass.getFields()).getItems();
        append("if(").append(sourceVarName).appendln(" == null)")
                .appendln("{")
                .appendln("    return null;")
                .appendln("}")
                .append(targeClassName).append(" ").append(targeVarName).append(" = new ").append(targeClassName).appendln("();");
        for (PsiField targetField : targetFields) {
            String targetFieldName = targetField.getName();
            appendCopyField(targeVarName, sourceVarName, targetFieldName).newLine();
        }
        append("return ").append(targeVarName).append(";");
        return this;
    }

    /**
     * 生成 target.setVar(source.getVar());
     * @param targeVarName
     * @param sourceVarName
     * @param fieldName
     * @return
     */
    private MethodTextBuilder appendCopyField(String targeVarName, String sourceVarName, String fieldName) {
        return append(targeVarName).append(".").append("set")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1, fieldName.length()))
                .append("(")
                .append(sourceVarName).append(".").append("get")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1, fieldName.length()))
                .append("());");
    }


    public MethodTextBuilder appendMethodTail() {
        return this.append("}");
    }

    public String genText() {
        return this.methodText.toString();
    }

}

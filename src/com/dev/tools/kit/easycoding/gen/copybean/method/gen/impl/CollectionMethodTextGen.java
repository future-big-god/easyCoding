package com.dev.tools.kit.easycoding.gen.copybean.method.gen.impl;

import com.dev.tools.kit.easycoding.gen.copybean.method.gen.MethodTextBuilder;
import com.dev.tools.kit.easycoding.gen.copybean.method.gen.MethodTextGen;
import com.intellij.psi.PsiMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:Collection方法文本生成器
 * @Author: zhangjianfeng
 * @Date: 2019-05-10
 */
public class CollectionMethodTextGen implements MethodTextGen {
    @Override
    public String genMethodText(PsiMethod psiMethod) {
        return getMethodText(psiMethod);
    }

    /**
     * 集合类型：List or Set
     * Queue的不确定性导致它无法被支持
     */
    private CollectionEnum collectionType;

    public CollectionMethodTextGen(CollectionEnum collectionType) {
        this.collectionType = collectionType;
    }

    @Override
    public boolean match(PsiMethod psiMethod) {
        return (psiMethod.getReturnType().getPresentableText().matches(this.collectionType.getName() + "<.+>"));
    }

    /**
     * 生成方法定义文本
     *
     * @param psiMethod
     * @return
     */
    private String getMethodText(PsiMethod psiMethod) {
        MethodTextBuilder methodTextBuilder = new MethodTextBuilder();
        methodTextBuilder.appendMethodHead(psiMethod).newLine();
        appendMethodBody(methodTextBuilder, psiMethod);
        methodTextBuilder.appendMethodTail();
        return methodTextBuilder.genText();
    }

    /**
     * 生成方法体
     * @param methodTextBuilder
     * @param psiMethod
     */
    private void appendMethodBody(MethodTextBuilder methodTextBuilder, PsiMethod psiMethod) {
        String sourceVarName = "source";
        methodTextBuilder.append(" if (CollectionUtils.isEmpty(").append(sourceVarName).append(")) {")
                .append("return new ")
                .append(this.collectionType.getImplName())
                .appendln("<>();")
                .appendln("}")
                .append("return source.stream().map(this::convert2")
                .append(genRealTypeShort(psiMethod.getReturnType().getPresentableText()))
                .append(").collect(Collectors.toList());");
    }


    /**
     * 生成实际类型（去除泛型）
     *  DataDto 将返回DataDto
     *  List<DataDto>  返回DataDto
     *  Map<String,DataDto>  返回DataDto
     * @param returnType
     * @return
     */
    private String genRealTypeShort(String returnType) {
        if (!returnType.contains("<"))
        {
            return returnType;
        }
        String tempStr = extractStr(returnType, "<(.+)>", 1);
        if (tempStr.contains(","))
        {
            return tempStr.split("[,]")[1];
        }
        return tempStr;
    }


    /**
     * 使用正则提取匹配字符串
     * @param source
     * @param reg
     * @param group
     * @return
     */
    private  String extractStr(String source, String reg, int group) {
        Matcher matcher = Pattern.compile(reg).matcher(source);
        if (matcher.find()) {
            return matcher.group(group);
        }
        return null;
    }

}

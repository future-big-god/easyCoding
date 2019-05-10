package com.zjf.easycoding.gen.copybean.method.gen.impl;

import com.intellij.psi.PsiMethod;
import com.zjf.easycoding.gen.copybean.method.gen.MethodTextBuilder;
import com.zjf.easycoding.gen.copybean.method.gen.MethodTextGen;

/**
 * @Description:对象方法文本生成器
 * @Author: zhangjianfeng
 * @Date: 2019-05-10
 */
public class ObjectMethodTextGen implements MethodTextGen {
    @Override
    public String genMethodText(PsiMethod psiMethod) {
        return getMethodText(psiMethod);
    }

    @Override
    public boolean match(PsiMethod psiMethod) {
        return true;
    }

    /**
     * 生成方法定义文本
     *
     * @param psiMethod
     * @return
     */
    private String getMethodText(PsiMethod psiMethod) {
        MethodTextBuilder methodTextBuilder = new MethodTextBuilder();
        methodTextBuilder.appendMethodHead(psiMethod)
                .newLine();
        methodTextBuilder.appendMethodBody4Obj(psiMethod.getProject(),psiMethod.getReturnType())
                .newLine()
                .appendMethodTail();
        return methodTextBuilder.genText();
    }

}

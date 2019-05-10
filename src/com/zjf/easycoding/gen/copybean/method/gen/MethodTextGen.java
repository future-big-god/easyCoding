package com.zjf.easycoding.gen.copybean.method.gen;


import com.intellij.psi.PsiMethod;

/**
 * @Description:方法文本生成器
 * @Author:zhangjianfeng5
 * @Date: 2019/5/10
 * @Time: 下午1:59
 */
public interface MethodTextGen {

    String genMethodText(PsiMethod psiMethod);

    boolean match(PsiMethod psiMethod);
}

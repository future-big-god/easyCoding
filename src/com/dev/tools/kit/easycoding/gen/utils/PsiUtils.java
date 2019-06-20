package com.dev.tools.kit.easycoding.gen.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

/**
 * @Description:PSI工具类
 * @Author: zhangjianfeng
 * @Date: 2019-06-20
 */
public class PsiUtils {

    /**
     * 根据psiFile获取项目的src目录
     * @param psiFile
     * @return
     */
    public static String getSrcPath(PsiFile psiFile) {
        //获取当前类文件的路径
        String classPath = psiFile.getVirtualFile().getPath();
        if(classPath==null){
            return null;
        }
        classPath=classPath.split("/src/main/java/")[0];
        if(classPath==null){
            return null;
        }
        return classPath + "/src/main/java/";
    }

    /**
     * 根据psiFile获取java类名（包含包路径）
     * @param psiFile
     * @return
     */
    public static String getClassFullName(PsiFile psiFile) {
        PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile;
        PsiClass[] classes = psiJavaFile.getClasses();
        if (classes == null || classes.length <= 0)
        {
            return null;
        }
        return classes[0].getQualifiedName();
    }
}

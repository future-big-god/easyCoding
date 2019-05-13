package com.dev.tools.kit.easycoding.gen.copybean;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.dev.tools.kit.easycoding.gen.copybean.method.gen.MethodTextGenHolder;

public class BeanCopyAction extends AnAction {


    private MethodTextGenHolder methodTextGenHolder;

    public BeanCopyAction() {
        this.methodTextGenHolder = new MethodTextGenHolder();
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            PsiMethod psiMethod = getPsiMethodFromContext(e);
            String methodText = this.methodTextGenHolder.getMethodTextGen(psiMethod).genMethodText(psiMethod);
            replaceMethod(psiMethod, methodText);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 替换方法体
     * @param psiMethod 目标方法
     * @param methodText 方法定义文本
     */
    private void replaceMethod(PsiMethod psiMethod, String methodText) {
        new WriteCommandAction.Simple(psiMethod.getProject(), psiMethod.getContainingFile()) {
            @Override
            protected void run() {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiMethod.getProject());
                PsiMethod toMethod = elementFactory.createMethodFromText(methodText, psiMethod);
                psiMethod.replace(toMethod);
            }
        }.execute();
    }

    /**
     * 获取当前鼠标所在位置的方法
     * @param e
     * @return
     */
    private PsiMethod getPsiMethodFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            e.getPresentation().setEnabled(false);
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt =  psiFile.findElementAt(offset);
        if (elementAt == null) {
            return null;
        }
        return PsiTreeUtil.getParentOfType(elementAt, PsiMethod.class);
    }

}

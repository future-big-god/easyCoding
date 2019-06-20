package com.dev.tools.kit.easycoding.gen.table.columns;

import com.dev.tools.kit.domain.FieldInfo;
import com.dev.tools.kit.domain.ModelInfo;
import com.dev.tools.kit.easycoding.gen.utils.PsiUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.List;

/**
 * @Description:表格列生成
 * @Author:zhangjianfeng5
 * @Date: 2019/6/20
 * @Time: 下午1:57
 */
public class TableColumnsGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        String srcRootPath = PsiUtils.getSrcPath(psiFile);
        String className = PsiUtils.getClassFullName(psiFile);
        try{
            List<FieldInfo> fieldInfoList = getFieldInfos(srcRootPath, className);
            StringBuilder html = new StringBuilder();
            html.append("[").append("\n");
            for (int i = 0; i < fieldInfoList.size(); i++) {
                FieldInfo fieldInfo = fieldInfoList.get(i);
                html.append("\t{").append("\n");
                html.append("\t\ttitle: '").append(fieldInfo.getDesc()).append("',").append("\n");
                html.append("\t\tkey: '").append(fieldInfo.getName()).append("',").append("\n");
                html.append("\t\talign: 'center'").append("\n");
                html.append("\t}");
                if (i != fieldInfoList.size() - 1)
                {
                    html.append(",");
                }
                html.append("\n");
            }
            html.append("]").append("\n");
            setSysClipboardText(html.toString());
            System.out.println(html.toString());
            Messages.showMessageDialog(project,
                    "生成代码已经复制到粘贴板",
                    "结果",
                    Messages.getInformationIcon());
        }catch (Exception ex){
            Messages.showMessageDialog(project,
                    "报错啦"+ex.getMessage(),
                    "结果",
                    Messages.getInformationIcon());
            return;
        }

    }

    private List<FieldInfo> getFieldInfos(String srcRootPath, String className) {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setActureType(className);
        modelInfo.parseFieldInfos(srcRootPath);
        return modelInfo.getFieldInfoList();
    }


    private void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }
}
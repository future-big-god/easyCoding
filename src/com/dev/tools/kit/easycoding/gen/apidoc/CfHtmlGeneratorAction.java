package com.dev.tools.kit.easycoding.gen.apidoc;

import com.dev.tools.kit.DocGenerator;
import com.dev.tools.kit.confluence.ConfluenceDocGenerator;
import com.dev.tools.kit.domain.DocInfo;
import com.dev.tools.kit.easycoding.gen.utils.PsiUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author zhoujun5
 * @date 2019-05-13 10:35
 */
public class CfHtmlGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null){
            return;
        }
        //获取当前类文件的路径
        String classPath = PsiUtils.getSrcPath(psiFile);
        if(classPath==null){
            return ;
        }
        SelectionModel selectionModel = editor.getSelectionModel();
        String methodName=selectionModel.getSelectedText();
        String interfaceName=((PsiJavaFileImpl)psiFile).getPackageName()+"/"+psiFile.getName().split(".java")[0]+"#"+methodName;
        try{
            DocGenerator docGenerator = new ConfluenceDocGenerator(classPath);
            DocInfo docInfo = docGenerator.generate(interfaceName);
            setSysClipboardText(docInfo.getContent());
        }catch (Exception ex){
            Messages.showMessageDialog(project,
                    "报错啦"+ex.getMessage(),
                    "结果",
                    Messages.getInformationIcon());
            return;
        }
        Messages.showMessageDialog(project,
                "接口html已经复制到粘贴板，去CF粘贴吧~~",
                "结果",
                Messages.getInformationIcon());
    }

    private void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }
}
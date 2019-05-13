package com.dev.tools.kit.easycoding.gen.apidoc;

import com.dev.tools.kit.DocGenerator;
import com.dev.tools.kit.confluence.ConfluenceDocGenerator;
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
import groovy.util.logging.Slf4j;

/**
 * @author zhoujun5
 * @date 2019-05-13 10:35
 */
@Slf4j
public class CfHtmlGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null){
            return;
        }
        SelectionModel selectionModel = editor.getSelectionModel();
        String methodName=selectionModel.getSelectedText();
        //获取当前类文件的路径
        String classPath = psiFile.getVirtualFile().getPath();
        if(classPath==null){
            return ;
        }
        classPath=classPath.split("/src/main/java/")[0];
        if(classPath==null){
            return ;
        }
        String interfaceName=((PsiJavaFileImpl)psiFile).getPackageName()+"/"+psiFile.getName().split(".java")[0]+"#"+methodName;
        //配置项目内的java路径 默认为/src/main/java/
        System.setProperty("project.java.path","/src/main/java/");
        try{
            DocGenerator docGenerator = new ConfluenceDocGenerator(classPath);
            docGenerator.write2Console(interfaceName);
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
}
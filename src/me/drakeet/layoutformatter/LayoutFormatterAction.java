package me.drakeet.layoutformatter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by drakeet on 16/4/8.
 */
public class LayoutFormatterAction extends AnAction {

    public LayoutFormatterAction() {
        // Set the menu item name.
        super("Layout _Formatter _Action");
    }


    public void actionPerformed(final AnActionEvent event) {
        final Project project = getEventProject(event);
        VirtualFile file = event.getData(LangDataKeys.VIRTUAL_FILE);
        System.out.println("All formatted files:");
        execute(project, file);
        systemReformat(event);
    }


    private void systemReformat(final AnActionEvent event) {
        event.getActionManager()
             .getAction(IdeActions.ACTION_EDITOR_REFORMAT)
             .actionPerformed(event);
    }


    private void execute(Project project, final VirtualFile file) {
        VirtualFile[] files = file.getChildren();
        if (files.length > 0) {
            for (VirtualFile _file : files) {
                if (Files.isXmlFileOrDir(_file)) {
                    execute(project, _file);
                }
            }
        } else {
            System.out.println(file.getPath());
            final Document document = FileDocumentManager.getInstance().getDocument(file);
            new WriteCommandAction.Simple(project) {
                @Override protected void run() throws Throwable {
                    String txt = document.getText();
                    document.setText(Formatter.apply(txt));
                }
            }.execute();
        }
    }


    @Override public void update(AnActionEvent event) {
        super.update(event);
        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        event.getPresentation().setVisible(Files.isXmlFileOrDir(file));
    }
}
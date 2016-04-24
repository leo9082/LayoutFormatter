package me.drakeet.layoutformatter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

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
        Editor editor = event.getData(LangDataKeys.EDITOR);
        if (editor != null) {
            final Document document = editor.getDocument();
            new WriteCommandAction.Simple(project) {
                @Override protected void run() throws Throwable {
                    String txt = document.getText();
                    document.setText(Formatter.apply(txt));
                    event.getActionManager()
                         .getAction(IdeActions.ACTION_EDITOR_REFORMAT)
                         .actionPerformed(event);
                }
            }.execute();
        } else {
            Messages.showMessageDialog(project, "Please focus in your editor and try again.",
                    "Error", null);
        }
    }


    @Override public void update(AnActionEvent event) {
        super.update(event);
        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        event.getPresentation().setVisible(isXmlFile(file));
    }


    private static boolean isXmlFile(@Nullable VirtualFile file) {
        return file != null && file.getName().endsWith(".xml");
    }
}
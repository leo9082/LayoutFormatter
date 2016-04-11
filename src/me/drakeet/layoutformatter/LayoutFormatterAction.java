package me.drakeet.layoutformatter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by drakeet on 16/4/8.
 */
public class LayoutFormatterAction extends AnAction {
    public LayoutFormatterAction() {
        // Set the menu item name.
        super("Layout _Formatter _Action");
        // Set the menu item name, description and icon.
    }


    public void actionPerformed(final AnActionEvent event) {
        final Project project = event.getData(PlatformDataKeys.PROJECT);
        if (event.getData(LangDataKeys.EDITOR) != null) {
            final Document document = event.getData(LangDataKeys.EDITOR).getDocument();
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override public void run() {
                    String txt = document.getText();
                    document.setText(Formatter.apply(txt));
                    event.getActionManager()
                         .getAction(IdeActions.ACTION_EDITOR_REFORMAT)
                         .actionPerformed(event);
                }
            });
        } else {
            Messages.showMessageDialog(project, "Please focus in your editor and try again.",
                    "Error", null);
        }
    }
}
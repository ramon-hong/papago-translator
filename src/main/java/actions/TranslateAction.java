package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import forms.config.PapagoTranslatorConfig;
import helper.Lang;
import helper.PapagoRequest;
import helper.Utils;

public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = CommonDataKeys.EDITOR.getData(event.getDataContext());
        if ( editor == null ) return;

        String text = editor.getSelectionModel().getSelectedText();
        if (text == null || text.equals(""))    return;

        PapagoTranslatorConfig config = PapagoTranslatorConfig.getInstance(event.getRequiredData(CommonDataKeys.PROJECT));

        PapagoRequest request = new PapagoRequest(
                config.getClientId(),
                config.getClientSecret(),
                Lang.getInstance(config.getFromIndex()),
                Lang.getInstance(config.getToIndex()));

        String translatedText = request.getTranslatedText(text);

        if (translatedText == null || translatedText.equals("")) return;

        Utils.UI.replaceSelectedText(editor, translatedText);
    }
}



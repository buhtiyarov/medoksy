package util;

import view.IRichEditor;
import view.RichEditor;

public class PluginFactory {
    public static IRichEditor createRichEditor() {
        return new RichEditor();
    }
}

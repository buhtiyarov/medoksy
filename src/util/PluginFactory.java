package util;

import view.IRichEditor;
import view.RichEditor;
import view.SHEF;

public class PluginFactory {
    public static IRichEditor createRichEditor() {
        return new SHEF();
    }

//     public static IPrintService createPrintService() {
//         return new DummyPrintService();
//     }
}

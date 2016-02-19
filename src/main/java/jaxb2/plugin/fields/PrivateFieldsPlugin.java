package jaxb2.plugin.fields;

import com.sun.codemodel.JMod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

public class PrivateFieldsPlugin extends Plugin {

    private static final String OPTION_NAME = "Xprivate-fields";

    public String getOptionName() {
        return OPTION_NAME;
    }

    public String getUsage() {
        return "-" + OPTION_NAME + ": Set fields access level to private";
    }

    /**
     * Plugin entry-point
     */
    @Override
    public boolean run(Outline outline, Options opt, ErrorHandler errorHandler)
            throws SAXException {
        for (ClassOutline classOutline : outline.getClasses()) {
            classOutline.implClass.fields().values()
                    .stream()
                    .filter(field -> JMod.STATIC != field.mods().getValue())
                    .filter(field -> JMod.PRIVATE != field.mods().getValue())
                    .forEach(field -> field.mods().setPrivate());
        }
        return true;
    }

}

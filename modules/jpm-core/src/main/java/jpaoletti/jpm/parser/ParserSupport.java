package jpaoletti.jpm.parser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import java.io.InputStream;
import java.io.InputStreamReader;
import jpaoletti.jpm.core.PresentationManager;
import jpaoletti.jpm.util.ResourceManager;

/**
 *
 * @author jpaoletti
 */
public abstract class ParserSupport implements PMParser {

    private XStream xstream;
    private PresentationManager pm;

    public ParserSupport(PresentationManager pm) {
        this.pm = pm;
    }

    protected void init() {
        xstream = new XStream(new JDomDriver());
    }

    @Override
    public Object parseFile(String filename) throws Exception {
        init();
        final InputStream is = ResourceManager.getInputStream(filename);
        final Object result = xstream.fromXML(new InputStreamReader(is), newObject());
        afterParse(result);
        return result;
    }

    @Override
    public void saveToFile(Object object, String filename) throws Exception {
        init();
        //todo finish
        xstream.toXML(object);
    }

    public XStream getXstream() {
        return xstream;
    }

    protected abstract Object newObject();

    public PresentationManager getPm() {
        return pm;
    }

    protected void afterParse(Object result) {
    }
}

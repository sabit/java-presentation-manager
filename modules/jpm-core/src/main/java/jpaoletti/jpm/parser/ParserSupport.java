package jpaoletti.jpm.parser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author jpaoletti
 */
public abstract class ParserSupport implements PMParser {

    private XStream xstream;

    protected void init() {
        xstream = new XStream(new JDomDriver());
    }

    @Override
    public Object parseFile(String filename) throws Exception {
        init();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        return xstream.fromXML(new InputStreamReader(is), newObject());
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
}

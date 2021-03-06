package jpaoletti.jpm.parser;

import jpaoletti.jpm.core.PresentationManager;
import jpaoletti.jpm.core.monitor.Monitor;

/**
 *
 * @author jpaoletti
 */
public class MonitorParser extends ParserSupport {

    public MonitorParser(PresentationManager pm) {
        super(pm);
    }

    @Override
    protected void init() {
        super.init();
        getXstream().alias("monitor", Monitor.class);
        getXstream().useAttributeFor(Monitor.class, "id");
    }

    @Override
    protected Object newObject() {
        return new Monitor(getPm());
    }
}

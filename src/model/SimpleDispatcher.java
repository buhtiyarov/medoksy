package model;

import java.util.ArrayList;
import java.util.Iterator;

abstract public class SimpleDispatcher {
    protected ArrayList<SimpleListener> listeners = new ArrayList<SimpleListener>();

    public void addListener(SimpleListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SimpleListener listener) {
        listeners.remove(listener);
    }

    protected void fireChanged() {
        Iterator<SimpleListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().changed();
        }
    }
}

package model;

public class ValueHolder {
    private Object value;
    private ValueLoader loader;

    public ValueHolder() {}

    public ValueHolder(ValueLoader loader) {
        this.loader = loader;
    }

    public Object getValue() {
        if (value == null && loader != null) {
            value = loader.load();
        }

        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void resetValue() {
        this.value = null;
    }
}

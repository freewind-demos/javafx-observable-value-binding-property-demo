package demo.simulator;

interface ReadOnlyProperty<T> extends ObservableValue<T> {
    Object getBean();
    String getName();
}

interface WritableValue<T> {
    T getValue();
    void setValue(T value);
}

interface Property<T> extends ReadOnlyProperty<T>, WritableValue<T> {
    void bind(ObservableValue<? extends T> observable);
    void unbind();
    boolean isBound();
    void bindBidirectional(Property<T> other);
    void unbindBidirectional(Property<T> other);
}



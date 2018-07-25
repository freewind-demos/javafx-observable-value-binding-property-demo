package demo.simulator;

interface Observable {
    void addListener(InvalidationListener listener);
    void removeListener(InvalidationListener listener);
}

interface InvalidationListener {
    void invalidated(Observable observable);
}

interface ObservableValue<T> extends Observable {
    void addListener(ChangeListener<? super T> listener);
    void removeListener(ChangeListener<? super T> listener);
    T getValue();
}

@FunctionalInterface
interface ChangeListener<T> {
    void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);
}

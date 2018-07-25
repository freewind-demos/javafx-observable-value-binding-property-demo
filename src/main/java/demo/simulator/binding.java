package demo.simulator;

import com.sun.javafx.binding.Logging;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

interface Binding<T> extends ObservableValue<T> {

    boolean isValid();

    void invalidate();

    ObservableList<?> getDependencies();

    void dispose();

}

class BindingHelperObserver implements InvalidationListener {

    private final WeakReference<Binding<?>> ref;

    public BindingHelperObserver(Binding<?> binding) {
        if (binding == null) {
            throw new NullPointerException("Binding has to be specified.");
        }
        ref = new WeakReference<>(binding);
    }

    @Override
    public void invalidated(Observable observable) {
        final Binding<?> binding = ref.get();
        if (binding == null) {
            observable.removeListener(this);
        } else {
            binding.invalidate();
        }
    }

}

abstract class StringBinding extends StringExpression implements Binding<String> {

    // 用于持有经过`computeValue`计算后的值，而它往往是根据自己绑定的几个dependencies来计算
    private String value;

    // 防止在计算过程中多次调用invalidate通知下游，使用它来确定状态
    private boolean valid = false;

    // 其实就是自己，只是为了防止memory leak，再使用WeakReference包装一下
    private BindingHelperObserver observer;

    @Override
    public void addListener(InvalidationListener listener) {
        // helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        // helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super String> listener) {
        // helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {
        // helper = ExpressionHelper.removeListener(helper, listener);
    }

    protected final void bind(Observable... dependencies) {
        if ((dependencies != null) && (dependencies.length > 0)) {
            if (observer == null) {
                observer = new BindingHelperObserver(this);
            }
            for (final Observable dep : dependencies) {
                dep.addListener(observer);
            }
        }
    }

    protected final void unbind(Observable... dependencies) {
        if (observer != null) {
            for (final Observable dep : dependencies) {
                dep.removeListener(observer);
            }
            observer = null;
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public ObservableList<?> getDependencies() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public final String get() {
        if (!valid) {
            value = computeValue();
            valid = true;
        }
        return value;
    }

    protected void onInvalidating() {
    }

    @Override
    public final void invalidate() {
        if (valid) {
            valid = false;
            onInvalidating();
            fireValueChangedEvent();
        }
    }

    private void fireValueChangedEvent() {
        // ExpressionHelper.fireValueChangedEvent
    }

    @Override
    public final boolean isValid() {
        return valid;
    }

    protected abstract String computeValue();

    @Override
    public String toString() {
        return valid ? "StringBinding [value: " + get() + "]"
                : "StringBinding [invalid]";
    }

}

class Bindings {

    public static StringBinding createStringBinding(final Callable<String> func, final Observable... dependencies) {
        return new StringBinding() {
            {
                bind(dependencies);
            }

            @Override
            protected String computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return "";
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        FXCollections.emptyObservableList()
                        : (dependencies.length == 1) ?
                        FXCollections.singletonObservableList(dependencies[0])
                        : new ImmutableObservableList<>(dependencies);
            }
        };
    }
}
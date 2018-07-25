package demo.simulator;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.lang.ref.WeakReference;
import java.text.Format;

import static demo.simulator.Helpers.___;

interface ObservableObjectValue<T> extends ObservableValue<T> {
    T get();
}

interface WritableObjectValue<T> extends WritableValue<T> {
    T get();
    void set(T value);
}

interface ObservableStringValue extends ObservableObjectValue<String> {
}

interface WritableStringValue extends WritableObjectValue<String> {
}

abstract class StringExpression implements ObservableStringValue {

    @Override
    public String getValue() {
        return get();
    }

    public final String getValueSafe() {
        final String value = get();
        return value == null ? "" : value;
    }

    public static StringExpression stringExpression(
            final ObservableValue<?> value) {
        // StringFormatter.convert
        throw ___;
    }

    public StringExpression concat(Object other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isEqualTo(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isEqualTo(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNotEqualTo(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNotEqualTo(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isEqualToIgnoreCase(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isEqualToIgnoreCase(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNotEqualToIgnoreCase(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNotEqualToIgnoreCase(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding greaterThan(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding greaterThan(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding lessThan(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding lessThan(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding greaterThanOrEqualTo(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding greaterThanOrEqualTo(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding lessThanOrEqualTo(final ObservableStringValue other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding lessThanOrEqualTo(final String other) {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNull() {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNotNull() {
        // Bindings.xxx
        throw ___;
    }

    public IntegerBinding length() {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isEmpty() {
        // Bindings.xxx
        throw ___;
    }

    public BooleanBinding isNotEmpty() {
        // Bindings.xxx
        throw ___;
    }
}

abstract class ReadOnlyStringProperty extends StringExpression implements ReadOnlyProperty<String> {
    @Override
    public String toString() {
        // "combine details of the bean to a string"
        throw ___;
    }
}


abstract class StringProperty extends ReadOnlyStringProperty implements Property<String>, WritableStringValue {

    @Override
    public void setValue(String v) {
        // set(v)
    }

    @Override
    public void bindBidirectional(Property<String> other) {
        // "Bindings.xxx"
    }

    public void bindBidirectional(Property<?> other, Format format) {
        // "Bindings.xxx"
    }

    public <T> void bindBidirectional(Property<T> other, StringConverter<T> converter) {
        // "Bindings.xxx"
    }

    @Override
    public void unbindBidirectional(Property<String> other) {
        // "Bindings.xxx"
    }

    public void unbindBidirectional(Object other) {
        // "Bindings.xxx"
    }

    @Override
    public String toString() {
        // combine more details of this bean to a string
        throw ___;
    }
}

@SuppressWarnings("ALL")
abstract class StringPropertyBase extends StringProperty {

    // 没有绑定其它对象时，使用该值来持有数据。有了对象，它就被抛弃了，直到解绑后重新启用。
    private String value;

    // 我关注的对象，默认没有，可以通过bind()方法绑定，以及unbind()方法解绑
    // 我的上游
    private ObservableValue<? extends String> observable = null;

    // 持有关心我的变化的人，当我绑定对象、换了对象、或者没对象时改变了value的值时，都会通知它
    // 它如果对我感兴趣，就可以过来查看我的新值
    // 我的下游
    private InvalidationListener listener = null;

    // 我是一个理性的人，当我发生变化时，我只会通知关注者一次，在他过来查看我的新值之前，我就算又有变化也不会催他。
    // 这个值就是为了记录“他过来看我了吗”
    private boolean valid = true;

    public StringPropertyBase() {
    }

    public StringPropertyBase(String initialValue) {
        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        // ExpressionHelper.addListener to create and add the listener
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        // ExpressionHelper.removeListener
    }

    @Override
    public void addListener(ChangeListener<? super String> listener) {
        // ExpressionHelper.addListener to create and add the listener
    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {
        // ExpressionHelper.removeListener
    }

    protected void fireValueChangedEvent() {
        // ExpressionHelper.fireValueChangedEvent
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    protected void invalidated() {
    }

    @Override
    public String get() {
        valid = true;
        return observable == null ? value : observable.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String newValue) {
        if (isBound()) {
            throw new java.lang.RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") + "A bound value cannot be set.");
        }
        if (StringUtils.equals(value, newValue)) {
            value = newValue;
            markInvalid();
        }
    }

    // 我是不是有对象的人
    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(ObservableValue<? extends String> newObservable) {
        if (newObservable == null) throw new NullPointerException("Cannot bind to null");
        if (!newObservable.equals(observable)) {
            // 先斩断与当前对象的联系（如果有的话）
            unbind();

            // 再与新的对象建立联系
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);

            // 通知我的关注者我发生了变化
            markInvalid();
        }
    }

    // 斩断与当前对象的联系
    // 斩断前还要先拿到对方的值留个记忆
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable.removeListener(listener);
            observable = null;
        }
    }

    @Override
    public String toString() {
        // "more detailed about this bean to a string"
        throw ___;
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<StringPropertyBase> wref;

        public Listener(StringPropertyBase ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            StringPropertyBase ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}


class SimpleStringProperty extends StringPropertyBase {

    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";

    private final Object bean;
    private final String name;

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    public SimpleStringProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public SimpleStringProperty(String initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public SimpleStringProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public SimpleStringProperty(Object bean, String name, String initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }
}
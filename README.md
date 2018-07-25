JavaFX ObservableValue/Binding/Property Demo
============================================

- `Observable`：可以让某一个值被监听，当它发生变化时会通知监听者
- `ObservableValue`：可以让一个值有类型，并且当它发生变化时，会把变化前后的值通知监听者
- `Property`：
    - 因为是property，所以可以:
        - 拿到bean对象
        - 拿到属性名
        - 拿到值
        - 赋值
    - 同时，为了方便属性之间的数据的自动传播，需要有绑定功能
        - 单向绑定和解绑
        - 双向绑定和解绑
        - 判断是否绑定
- `Binding`: 表达了一个`ObservableValue`与另一个或多个`ObservableValue`之间的依赖关系。当dependencies有变化时，会触发当前的对象运行`computeValue`方法重新计算数据

为了正好的理解这些类之间的关系，我把相关的典型代码抽出来经过简化后，放在了`demo.simulator`包下，可能通过它们来理解这几个概念之间的关系。

通过分析`SimpleStringProperty`和`StringBinding`的实现，发现有Java的实现过于复杂。
主要原因是Java的表达能力弱的语法，使得同类型的类有很多重复的代码（比如`SimpleStringProperty`和`SimpleBooleanProperty`中），而且难以简化。
如果像Kotlin/Scala那样，有Extension method，以及更强大的函数式支持，这些代码可以简化很多。
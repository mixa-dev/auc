package nl.mixa.auc.util;

@FunctionalInterface
public interface TriFunction<T, T1, T2, R> {

    R apply(T t, T1 t1, T2 t2);

}

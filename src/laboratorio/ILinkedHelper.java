
package laboratorio;

public interface ILinkedHelper<T> {
    default void handle(T node) {
        System.out.println(node);
    }

    default boolean compare(T a, T b) {
        return a.equals(b);
    }
}

import java.util.List;

public class MyStack {
    private final Object[] massive;
    private int size;
    public MyStack() {
        massive = new Object[255];
        size = 0;
    }

    public void push(Object element) {
        if (size < massive.length) {
            massive[size] = element;
            size++;
        }
    }

    public Object pop() {
        if(size>0) {
            size--;
            return massive[size];
        }
        return null;
    }
}
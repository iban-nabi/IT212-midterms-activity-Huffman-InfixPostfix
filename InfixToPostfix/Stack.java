package InfixToPostfix;

public interface Stack<T> {
    public int size(); /* returns the size of the stack */
    public boolean isEmpty(); /* checks if empty */
    public T pop() throws StackException;
    public void push(T item) throws StackException;
    public T peek() throws StackException;
}
class StackException extends RuntimeException {
    public StackException(String err) {
        super(err);
    }
}

package InfixToPostfix;

public class MyStack <T> implements Stack<T> {
    int top=-1;
    int size;
    int capacity;
    T[] stack;

    public MyStack(int size) {
        this.size = size;
        this.capacity=size;
        stack = (T[])new Object[size];
    }

    public void countCapacity(){
        int count=0;
        for(int i=0; i<size();i++){
            if(stack[i]!=null){
                count++;
            }else{
                break;
            }
        }
        capacity=size-count;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return stack[0] == null;
    }

    @Override
    public T pop() throws StackException {
        T item;
        if (isEmpty()) throw new StackException("InfixToPostfix.Stack underflow.");
        item = stack[top];
        stack[top--] = null;
        return item;
    }

    @Override
    public void push(T item) throws StackException {
        if(stack[0]!=null){
            countCapacity();
        }

        if (capacity==0)throw new StackException("InfixToPostfix.Stack overflow.");
        stack[++top] = item;
    }

    @Override
    public T peek() throws StackException {
        if(isEmpty()){
            throw new StackException("InfixToPostfix.Stack is Empty");
        }
        return stack[top];
    }

    public String getString() {
        String contents = "";
        for (T t : stack) {
            if (t == null)
                contents = contents + "";
            else
                contents = contents + t;
        }
        return contents;
    }
}

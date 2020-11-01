public class Main {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        Core core = new Core(stack);
        core.printSystemCallsList();

        stack.push(2);
        stack.push('C');

        core.performSystemCall(2);

        stack.push("hnytgj");
        stack.push("fvdrfd");
        stack.push(2);
        stack.push("eqew");

        core.performSystemCall(0);
    }
}
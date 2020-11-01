import java.util.HashMap;

public class Core {
    private HashMap<Integer, SystemCall> tableCall;
    private MyStack stack = new MyStack();

    public Core(MyStack stack) {
        tableCall = new HashMap<>();
        tableCall.put(0, new SystemCall(0, new Object[]{"", 0, "", ""}));
        tableCall.put(1, new SystemCall(1, new Object[]{"", ""}));
        tableCall.put(2, new SystemCall(2, new Object[]{' ', 0}));
        tableCall.put(3, new SystemCall(3, new Object[]{0, 0, 0}));
        tableCall.put(4, new SystemCall(4, new Object[]{0.0, "", ""}));
        this.stack = stack;
    }

    public void performSystemCall(int id) {
        int size = 0;
        MyStack mm = new MyStack();
        Object cash = stack.pop();

        while(cash!=null){
            size++;
            mm.push(cash);
            cash = stack.pop();
        }
        for(int i = 0 ; i<size; i++){
            stack.push(mm.pop());
        }
        if (size == 0) {
            System.out.println("Стэк пуст");
            return;
        }
        int stackSize = size;
        Object[] args = new Object[stackSize];

        for (int i = 0; i < stackSize; i++) {
            args[i] = stack.pop();
        }

        SystemCall currentCall = new SystemCall(id, args);
        if (!tableCall.containsKey(id)) {
            System.out.println("Id не существует");
            return;
        }

        Object[] argsArr = currentCall.getArgs();
        if (argsArr.length != tableCall.get(id).getArgs().length) {
            System.out.println("Не правильное количество аргументов");
            return;
        }

        for (int i = 0; i < argsArr.length; i++) {
            if (argsArr[i].getClass() != tableCall.get(id).getArgs()[i].getClass()) {
                System.out.println("Неверный набор аргументов");
                return;
            }
        }
        System.out.println("Выполняется системный вызов с id " + id);
    }

    public void printSystemCallsList() {
        for (int i = 0; i < tableCall.size(); i++) {
            StringBuilder strB = new StringBuilder("");
            strB.append("Номер вызова: ");
            strB.append(i);
            strB.append("\n");
            strB.append("Аргументы: ");

            for (int j = tableCall.get(i).getArgs().length-1; j >= 0; j--) {
                strB.append(tableCall.get(i).getArgs()[j].getClass().getSimpleName());
                strB.append(", ");
            }

            strB.append("\n\n");
            System.out.println(strB.toString());
        }
    }
}
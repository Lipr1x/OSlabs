import java.util.ArrayList;
import java.util.Random;

public class SystemCore {
    private ArrayList<SystemProcess> systemProcesses = new ArrayList<>();
    private Random random = new Random();
    private int timeOfProcess = 40;
    private int differenceTime = 10;
    private int circle = 1;

    private void createProcesses(int sizeOfProcess) {
        for (int i = 0; i < sizeOfProcess; i++) {
            int priority = getRandomNumber();
            SystemProcess systemProcess = new SystemProcess(systemProcesses.size(), priority);
            System.out.println(systemProcesses.size() + " процесс с приоритеом " + priority + " создан");
            systemProcesses.add(systemProcess);
        }
    }

    private void planning() {
        while (!systemProcesses.isEmpty()) {
            System.out.println("");
            System.out.println(circle + " цикл запущен");
            for (int i = 0; i < systemProcesses.size(); i++) {
                SystemProcess systemProcess = systemProcesses.get(i);
                systemProcess.load(timeOfProcess + systemProcess.getPriority() * differenceTime);
                if (systemProcess.getIsEmpty()) {
                    systemProcesses.remove(i);
                    i--;
                }
            }
            circle++;
        }
    }

    private int getRandomNumber() {
        return random.nextInt(3);
    }

    public void launch() {
        int sizeOfProcesses = 4 + random.nextInt(3);
        System.out.println(sizeOfProcesses + " процессов");
        createProcesses(sizeOfProcesses);
        planning();
    }
}
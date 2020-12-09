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
            for (int j = 0; j < systemProcesses.size(); j++) {
                SystemProcess systemProcess = systemProcesses.get(j);
                int maxTimeOfProcess = timeOfProcess + systemProcess.getPriority() * differenceTime;

                for (int i = 0; i < systemProcess.systemFlows.size(); i++) {
                    SystemFlow systemFlow = systemProcess.systemFlows.get(i);
                    if (systemFlow.getMaxTime() > maxTimeOfProcess) {
                        systemFlow.cutMaxTime(maxTimeOfProcess);
                        System.out.println("Обнаружено прерывание, выделенное время: " + maxTimeOfProcess + ". индекс потока " + systemFlow.getTid() + " процесса с индексом " + systemProcess.potokid + ". Оставшееся время: " + systemFlow.getMaxTime() + " Изначальное время: " + systemFlow.getYetTime());
                        break;
                    } else if(systemFlow.getMaxTime() == maxTimeOfProcess){
                        systemFlow.launch(systemProcess.potokid);
                        systemProcess.systemFlows.remove(systemFlow);
                        break;
                    }
                    else  {
                        maxTimeOfProcess -= systemFlow.getMaxTime();
                        systemFlow.launch(systemProcess.potokid);
                        systemProcess.systemFlows.remove(systemFlow);
                        i--;
                    }
                }

                if (systemProcess.getIsEmpty()) {
                    systemProcesses.remove(j);
                    j--;
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
import java.util.ArrayList;
import java.util.Random;

public class SystemProcess {

    private ArrayList<SystemFlow> systemFlows = new ArrayList<>();
    private int potokid;
    private int priority;
    private Random random = new Random();
    private int maxSizeOfFlow = 5;
    private int maxTimeOfFlow = 80;
    private boolean isEmpty = false;

    public SystemProcess(int potokid, int priority) {
        this.potokid = potokid;
        this.priority = priority;
        crateSystemFlows();
    }

    private int createRandomNumber(int number) {
        return 1 + random.nextInt(number - 1);
    }

    private void crateSystemFlows() {
        for (int i = 0; i < createRandomNumber(maxSizeOfFlow); i++) {
            SystemFlow systemFlow = new SystemFlow(systemFlows.size(), createRandomNumber(maxTimeOfFlow));
            systemFlows.add(systemFlow);
        }
    }

    public void load(int maxTimeOfProcess) {
        for (int i = 0; i < systemFlows.size(); i++) {
            SystemFlow systemFlow = systemFlows.get(i);
            if (systemFlow.getMaxTime() > maxTimeOfProcess) {
                systemFlow.cutMaxTime(maxTimeOfProcess);
                System.out.println("Обнаружено прерывание, выделенное время: " + maxTimeOfProcess + ". индекс потока " + systemFlow.getTid() + " процесса с индексом " + potokid + ". Оставшееся время: " + systemFlow.getMaxTime() + " Изначальное время: " + systemFlow.getYetTime());
                break;
            } else if(systemFlow.getMaxTime() == maxTimeOfProcess){
                systemFlow.launch(potokid);
                systemFlows.remove(systemFlow);
                break;
            }
            else  {
                maxTimeOfProcess -= systemFlow.getMaxTime();
                systemFlow.launch(potokid);
                systemFlows.remove(systemFlow);
                i--;
            }
        }
        if (systemFlows.isEmpty()) {
            isEmpty = true;
        }
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {return priority;}

    public boolean getIsEmpty() {
        return isEmpty;
    }
}
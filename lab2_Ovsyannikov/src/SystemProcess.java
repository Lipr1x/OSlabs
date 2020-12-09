import java.util.ArrayList;
import java.util.Random;

public class SystemProcess {

    public ArrayList<SystemFlow> systemFlows = new ArrayList<>();
    public int potokid;
    private int priority;
    private Random random = new Random();
    private int maxSizeOfFlow = 5;
    private int maxTimeOfFlow = 80;

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

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {return priority;}

    public boolean getIsEmpty() {
        return systemFlows.isEmpty();
    }
}
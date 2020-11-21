public class SystemFlow {
    private int tid;
    private int yetTime;
    private int maxTime;

    SystemFlow(int tid, int maxTime) {
        this.yetTime = maxTime;
        this.maxTime = maxTime;
        this.tid = tid;
    }

    public int getTid() {
        return tid;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void launch(int potokid) {
        System.out.println("поток с индексом " + tid +  " процесса с индексом " + potokid +" выполнился успешно за время " + maxTime);
    }

    public void cutMaxTime(int time) {
        maxTime -= time;
    }

    public int getYetTime() {
        return yetTime;
    }
}
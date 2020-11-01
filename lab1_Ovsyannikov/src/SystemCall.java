public class SystemCall {
    private int id;
    private Object[] args;

    public SystemCall(int id, Object[] args){
        this.id = id;
        this.args = args;
    }
    public Object[] getArgs(){
        return args;
    }
    public int getId(){
        return id;
    }
}

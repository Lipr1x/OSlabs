public class Page {

    private final int bits;
    private int physicalPageNumber;
    private long timeLastEdit;
    private long timeLastRead;
    private int pageSize;
    private final TableOfPage tableOfPage;
    private final Process process;
    public boolean clockFlag = true;

    public Page(int bits, TableOfPage tableOfPage, Process process, int pageNumber) {
        this.bits = bits;
        this.tableOfPage = tableOfPage;
        this.process = process;
        this.physicalPageNumber = pageNumber;
        this.pageSize = TableOfPage.getSize();
        timeLastRead = System.currentTimeMillis();
        timeLastEdit = System.currentTimeMillis();
    }

    public long getLastAppeal() {
        if (timeLastRead > timeLastEdit)
            return timeLastRead;
        return timeLastEdit;
    }

    public void setBytes(int virtualPosition, byte[] data) {
        timeLastEdit = System.currentTimeMillis();
        tableOfPage.setBytes(this, virtualPosition, data);
    }

    public boolean isInPhysicalMemory() {
        if (physicalPageNumber > -1)
            return true;
        return false;
    }

    public void delete() {
        tableOfPage.delete(this);
    }

    public String print() {
        String str = "идентификация страницы " + physicalPageNumber + "\n";
        if (isInPhysicalMemory()) {
            str += "в физической памяти\n";
            for (int i = 0; i < pageSize; i++) {
                str += tableOfPage.physicalMemory[physicalPageNumber * pageSize + i] + " ";
                if ((i + 1) % bits == 0)
                    str += "\n";
            }
            str += "\n";
        } else {
            str = "в файле подкачки\n";
            byte[] bytes = tableOfPage.virtualPages.get(this);
            for (int i = 0; i < pageSize; i++) {
                str += bytes[i] + " ";
                if ((i + 1) % bits == 0)
                    str += "\n";
            }
            str += "\n";
        }
        return str;
    }

    public int getPhysicalPageNumber() {
        return physicalPageNumber;
    }

    public void setPhysicalPageNumber(int number) {
        physicalPageNumber = number;
    }

}
import java.util.*;

public class MemoryManagement {

    private List<Process> processes;
    private TableOfPage tableOfPage;
    private int processesNumber;
    private int k;
    private boolean launch;
    private boolean pageIsDeleted;
    private int tasksNumber;

    public MemoryManagement() {
        Random rnd = new Random();
        processes = new ArrayList<Process>();
        processesNumber = rnd.nextInt(3) + 2;
        tableOfPage = new TableOfPage();
        k = 0;
        launch = false;
        pageIsDeleted = false;
        tasksNumber = rnd.nextInt(3) + 4;
    }

    public String performance() {
        String str = "";
        for (int i = 0; i < processesNumber; i++) {
            Process process = new Process(this);
            processes.add(process);
            str = "Создается процесс - " + "\n" + process.print(this) + "\n";
        }
        return str;
    }

    public String continuation() {
        if (processes.size() > 0) {
            if (k < processes.size()) {
                Process curProcess = processes.get(k);
                String str = "Выполнение\n";
                if (launch) {
                    Random rnd = new Random();
                    if (tasksNumber > 0) {
                        for (int i = 0; i < curProcess.getPages().size(); i++) {
                            if (rnd.nextBoolean()) {
                                byte[] bytes = new byte[rnd.nextInt(TableOfPage.getSize())];
                                rnd.nextBytes(bytes);
                                curProcess.getPages().get(i).setBytes(rnd.nextInt(TableOfPage.getSize()), bytes);
                            }
                        }
                        tasksNumber--;
                    } else {
                        for (int i = 0; i < curProcess.getPages().size(); i++) {
                            curProcess.getPages().get(i).delete();
                        }
                        pageIsDeleted = true;
                    }
                    k++;
                }
                launch = !launch;
                str += curProcess.print(this);
                if (pageIsDeleted) {
                    str += "Завершение работы процесса";
                    processes.remove(curProcess);
                }
                return str;
            } else {
                k = 0;
                return continuation();
            }
        } else {
            return "Выполнение процессов завершено";
        }
    }

    private List<Page> countPhysicalMemory(Process process, int pageCount) {
        List<Page> resultList = new ArrayList<Page>();
        for (int i = 0; i < pageCount; i++) {
            int accessiblePageNumber = tableOfPage.getAccessiblePhysicalPageNumber();
            Page page = new Page(tableOfPage.getBits(), tableOfPage, process, accessiblePageNumber);
            resultList.add(page);
            tableOfPage.getPhysicalPages()[accessiblePageNumber] = page;
            tableOfPage.clear(page);
        }
        return resultList;
    }

    public List<Page> countMemory(Process process) {
        Random rnd = new Random();
        double pageCountDiv =  rnd.nextInt(64) + 64 /  tableOfPage.getSize();
        int pageCountMod =  rnd.nextInt(64) + 64 %  tableOfPage.getSize();
        int pageCountTotal = (int) pageCountDiv;
        if (pageCountMod > 0) {
            pageCountTotal++;
        }
        if (tableOfPage.getFreePhysicalPageCount() >= pageCountTotal) {
            List<Page> memoryPages = countPhysicalMemory(process, pageCountTotal);
            return memoryPages;
        } else {
            ArrayList<Page> memoryPages = new ArrayList<Page>();
            int physicalPageCount = tableOfPage.getFreePhysicalPageCount();
            List<Page> pages = countPhysicalMemory(process, physicalPageCount);
            memoryPages.addAll(pages);
            for (int i = 0; i < pageCountTotal - physicalPageCount; i++) {
                int physicalID = tableOfPage.unloadPage(tableOfPage.getUsedPage());
                Page memoryPage = new Page(tableOfPage.getBits(), tableOfPage, process, physicalID);
                memoryPages.add(memoryPage);
                tableOfPage.getPhysicalPages()[physicalID] = memoryPage;
                tableOfPage.clear(memoryPage);
            }
            return memoryPages;
        }
    }

    public String statusPhysicalMemory() {
        String str = "статус физ. памяти - \n";
        for (int i = 0; i < tableOfPage.getPhysicalPages().length; i++) {
            if (tableOfPage.getPhysicalPages()[i] == null) {
                str += "Страница свободна\n";
                for (int j = 0; j < tableOfPage.getSize(); j++) {
                    str += tableOfPage.getPhysicalMemory()[j + tableOfPage.getSize() * i] + " ";
                    if ((j + 1) % tableOfPage.getBits() == 0)
                        str += "\n";
                }
            } else
                str += tableOfPage.getPhysicalPages()[i].print();
        }
        return str;
    }

    public String statusVirtualMemory() {
        String str = "статус файла подкачки - \n";
        Set<Page> pages = tableOfPage.getVirtualPages().keySet();
        Iterator<Page> pageIterator = pages.iterator();
        while (pageIterator.hasNext()) {
            Page page = pageIterator.next();
            if (!page.isInPhysicalMemory())
                str += page.print();
        }
        str += "\n";
        return str;
    }

    public boolean isPageIsDeleted() {
        return pageIsDeleted;
    }
}
package daysleeper.project.forex.tradelog.utilies;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
    private final int size;
    private final int items;

    public Pagination(int items, int size) {
        this.items = items;
        this.size = size;
    }

    public int getPages() {
        if (this.items % this.size == 0) {
            return this.items / this.size;
        } else {
            return this.items / this.size + 1;
        }
    }

    public Map<Integer, Integer> getPageMap() {
        Map<Integer, Integer> pageMap = new HashMap<>();
        int pages = getPages();
        for (int i = 1; i <= pages; i++) {
            int from = (i - 1) * this.size;
            pageMap.put(i, from);
        }
        return pageMap;
    }

}

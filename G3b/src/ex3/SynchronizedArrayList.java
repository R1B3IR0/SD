package ex3;

import java.util.ArrayList;

public class SynchronizedArrayList {
    private ArrayList<String> list = new ArrayList<>();

    public synchronized void add(String s) {
        list.add(s);
    }

    public synchronized ArrayList<String> getList() {
        return list;
    }
}

package ex3;

import java.util.ArrayList;

public class ObjSharing {
    public static void main(String[] args) {
        int NThreads = 5;
        SynchronizedArrayList synchronizedList = new SynchronizedArrayList();

        Worker[] workers = new Worker[NThreads];

        for(int i = 0; i < NThreads; i++) {
            workers[i] = new Worker(synchronizedList, i);
            workers[i].start();
        }

        for (int i = 0; i < NThreads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> asFrases = synchronizedList.getList();

        // Group the phrases by thread
        ArrayList<ArrayList<String>> groupedFrases = new ArrayList<>();

        for (int i = 0; i < NThreads; i++) {
            groupedFrases.add(new ArrayList<>());
        }

        for (String frase : asFrases) {
            groupedFrases.get(Integer.parseInt(frase.split(" ")[4])).add(frase);
        }

        // Print the grouped phrases
        for (int i = 0; i < NThreads; i++) {
            System.out.println("Thread " + i + ":");
            ArrayList<String> threadFrases = groupedFrases.get(i);
            for (String frase : threadFrases) {
                System.out.println(frase);
            }
            System.out.println();
        }
    }
}

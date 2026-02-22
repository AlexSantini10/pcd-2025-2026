package pcd.lab01.ex01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SorterThread extends  Thread {

    int [] number_list;

    public SorterThread(String name, int [] number_list) {
        super(name);
        this.number_list = number_list;
    }

    @Override
    public void run() {
        Arrays.sort(number_list, 0, number_list.length);
    }

    public int[] getSorted() {
        return number_list;
    }
}

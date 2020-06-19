package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        try{
            int lengthX = x.size();
            int lengthY = y.size();

            if (lengthY == 0 && lengthX != 0)
                return false;

            int i = 0;
            int j = 0;

            while (i < lengthX){
                while (j < lengthY){
                    if (x.get(i).equals(y.get(j))) {
                        j++;
                        break;
                    }
                    else {
                        y.remove(j);
                        lengthY--;
                    }
                    if (lengthY < lengthX)
                        return false;
                }
                i++;
            }
            return true;

        } catch (NullPointerException e){
            throw new IllegalArgumentException();
        }
    }
}

package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {

        /* ---- Check if the size of array matches for pyramid building ---- */

        /* And count rows and columns number */
        int rows = 0;
        int elementsInRow = 1;
        int inputNumbersLength = inputNumbers.size();

        while (inputNumbersLength > 0){
            inputNumbersLength -= elementsInRow;
            rows++;
            elementsInRow++;
        }

        /* columns = 2 * (elementsInRow - 1) - 1 = 2 * elementsInRow - 3 */
        int columns = 2 * elementsInRow - 3;

        /* ---- Exceptional cases ---- */

        /* If one of the elements is a null */
        for (Integer inputNumber : inputNumbers) {
            if (inputNumber == null)
                throw new CannotBuildPyramidException();
        }

        /* If size of array doesn't matches */
        if (inputNumbersLength < 0)
            throw new CannotBuildPyramidException();

        /* ---- Sort of given array ---- */
        inputNumbersLength = inputNumbers.size();
        for (int i = 0; i < inputNumbersLength-1; i++){
            for (int j = i+1; j < inputNumbersLength; j++){
                if (inputNumbers.get(i) > inputNumbers.get(j)){
                    int cash = inputNumbers.get(i);
                    inputNumbers.set(i, inputNumbers.get(j));
                    inputNumbers.set(j, cash);
                }
            }
        }

        /* ---- Filling out an output array ---- */

        int[][] answer = new int[rows][columns];

        /* At first with zeros */
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < columns; j++)
                answer[i][j] = 0;

        /* And then with pyramid values */
        elementsInRow = 1;
        int startIndex = columns / 2;
        int currentInputNumbersIndex = 0;

        for (int rowIndex = 0; rowIndex < rows; rowIndex++){
            int currentIndex = startIndex;
            for (int j = 0; j < elementsInRow; j++){
                answer[rowIndex][currentIndex] = inputNumbers.get(currentInputNumbersIndex);
                currentInputNumbersIndex++;
                currentIndex += 2;
            }
            startIndex--;
            elementsInRow++;
        }

        return answer;
    }

}
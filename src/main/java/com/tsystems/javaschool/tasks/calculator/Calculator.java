package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        /*
            This function is a try of Recursive descent parser algorithm realization.
            It works but very ineffective cause I didn't had enough time to optimize it.
            Anyway I hope that I've chosen correct way of solving this task.
        */

        if (statement == null || statement.length() == 0)
            return null;

        char[] allSigns = {'+', '-', '/', '*', '(', ')', '.'};
        char[] chars = statement.toCharArray();

        String number = "";
        boolean hasDot = false;

        ArrayList<String> allElementsList = new ArrayList<>(); // all elements in original order

        /* ---- Walkthrough the statement and write all signs and numbers into the allElementsList ---- */

        for (char element : chars){

            boolean inSigns = false;
            for (char symbol : allSigns)
                if (element == symbol) {
                    inSigns = true;
                    break;
                }

            /* Add a char to allElementsList if it belongs to valid signs */
            if (inSigns) {
                if (!number.isEmpty()) {
                    if (element == '.') {
                        if (hasDot)
                            return null;
                        else {
                            number += element;
                            hasDot = true;
                        }
                    }
                    else {
                        allElementsList.add(number);
                        number = "";
                        hasDot = false;
                        allElementsList.add(Character.toString(element));
                    }
                }
                else
                    allElementsList.add(Character.toString(element));
            }

            /* Collect digits in a number */
            else if (Character.isDigit(element)){
                number += element;
            }
            else
                return null;
        }

        if (!number.isEmpty())
            allElementsList.add(number);

        /* -------------------------------------------- */
        /* ---- Recursive descent parser algorithm ---- */
        /* -------------------------------------------- */

        /* Create two data stacks and map with signs priorities */
        Stack<Double> numbers = new Stack<>();
        Stack<String> signs = new Stack<>();
        HashMap<String, Integer> signPriority = new HashMap<>();

        signPriority.put("+", 1);
        signPriority.put("-", 1);
        signPriority.put("*", 2);
        signPriority.put("/", 2);

        /* Cash variables */
        double tmp1;
        double tmp2;
        String sgn;

        /* ---- Walkthrough all the elements ---- */
        for(String element : allElementsList){
            boolean isNumber = Character.isDigit(element.charAt(0));
            if (isNumber){
                try{
                    numbers.push(Double.parseDouble(element));
                } catch (NumberFormatException e){
                    return null;
                }
            }
            else{
                if (!signPriority.containsKey(element)) {

                    if (element.equals(")")) {
                        sgn = signs.pop();
                        while (!sgn.equals("(")) {
                            tmp1 = numbers.pop();
                            tmp2 = numbers.pop();

                            switch (sgn) {
                                case ("+"):
                                    numbers.push(tmp1 + tmp2);
                                    break;
                                case ("-"):
                                    numbers.push(tmp2 - tmp1);
                                    break;
                                case ("*"):
                                    numbers.push(tmp1 * tmp2);
                                    break;
                                case ("/"):
                                    if (tmp1 == 0)
                                        return null;
                                    else
                                        numbers.push(tmp2 / tmp1);
                                    break;
                            }
                            sgn = signs.pop();
                        }
                    }
                    else
                        signs.push(element);

                }
                else {
                    boolean isElementpushed = false;
                    if (!signs.isEmpty()) {
                        while (signPriority.containsKey(signs.lastElement())) {
                            if (signPriority.get(element) <= signPriority.get(signs.lastElement())) {
                                try{
                                    tmp1 = numbers.pop();
                                    tmp2 = numbers.pop();
                                    sgn = signs.pop();
                                    switch (sgn) {
                                        case ("+"):
                                            numbers.push(tmp1 + tmp2);
                                            break;
                                        case ("-"):
                                            numbers.push(tmp2 - tmp1);
                                            break;
                                        case ("*"):
                                            numbers.push(tmp1 * tmp2);
                                            break;
                                        case ("/"):
                                            if (tmp1 == 0)
                                                return null;
                                            else
                                                numbers.push(tmp2 / tmp1);
                                            break;
                                    }
                                } catch (EmptyStackException e){
                                    return null;
                                }
                            }
                            else {
                                signs.push(element);
                                isElementpushed = true;
                                break;
                            }

                            if (signs.isEmpty())
                                break;
                        }
                    }
                    if (!isElementpushed)
                        signs.push(element);
                }
            }
        }

        /* Make calculation with all the remaining values in stack */
        while (!signs.isEmpty()) {
            try {
                sgn = signs.pop();
                tmp1 = numbers.pop();
                tmp2 = numbers.pop();
                switch (sgn) {
                    case ("+"):
                        numbers.push(tmp1 + tmp2);
                        break;
                    case ("-"):
                        numbers.push(tmp2 - tmp1);
                        break;
                    case ("*"):
                        numbers.push(tmp1 * tmp2);
                        break;
                    case ("/"):
                        if (tmp1 == 0)
                            return null;
                        else
                            numbers.push(tmp2 / tmp1);
                        break;
                }
            } catch (EmptyStackException e){
                return null;
            }
        }

        String answer = Double.toString(numbers.pop());

        /* If an answer has no fractional part, remove it */
        if (answer.charAt(answer.length()-1) == '0' && answer.charAt(answer.length()-2) == '.')
            answer = answer.substring(0, answer.length()-2);

        return answer;
    }

}

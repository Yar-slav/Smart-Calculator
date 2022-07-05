package calculator;


import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        do {
            showResult();
        } while (true);

    }

    static void showResult() {
        System.out.println(Calculate.calculate());
    }


}





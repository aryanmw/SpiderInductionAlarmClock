package com.wadhavekar.clockit.Alarm;

import java.util.ArrayList;
import java.util.Random;

public class MathQuestion {
    public MathQuestion() {
    }

    String[] operations = {"+","*"};

    Random rand = new Random();
    int int1 = rand.nextInt(200);
    int int2 = rand.nextInt(200);

    int opArrayVal = rand.nextInt(4);

    public int getAnswer(){
        int answer;
        if (opArrayVal == 0){
            answer = int1+int2;
        }
        else {
            answer = int1*int2;
        }

        return answer;
    }

    public String getQuestion(){
        return int1+" "+operations[opArrayVal]+" "+int2;
    }

}


package com.example.newscoccer.indenpent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InstanceofTest {



    @Test
    @DisplayName("")
    public void instanceofTest() throws Exception{
        R lr = new LR();
        R rr = new RR();


        if(lr instanceof LR) System.out.println("true");
        else System.out.println("false");

        if(rr instanceof LR) System.out.println("true");
        else System.out.println("false");


    }

    static class R{
        int a;
    }

    static class LR extends R{
        int l;
    }

    static class RR extends R{
        int r;
    }

}

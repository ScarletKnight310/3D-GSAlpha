package revamp;

import revamp.base.Shape;

public class Test {
    public static void main(String[] args)
    {
        Shape base = new Shape();
        for(Double p: base.fixedpoint)
        {
            System.out.println(p);
        }
    }
}

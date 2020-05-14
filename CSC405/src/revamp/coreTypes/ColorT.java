package revamp.coreTypes;

public class ColorT {
    public int r;
    public int g;
    public int b;

    public ColorT()
    {
        r = -1;
        g = -1;
        b = -1;
    }

    public ColorT(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public boolean isDef(){
        return (r == -1) && (g == -1) && (b == -1);
    }

    public boolean isValid(){
        return (r >= 0 && r < 256) && (g >= 0 && g < 256) && (b >= 0 && b < 256);
    }
}

package ImageEditor;


/**
 * Created by Ty on 9/13/2017.
 */

public class Pixel
{
    private int red;
    private int green;
    private int blue;

    public Pixel(int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed()
    {
        return red;
    }
    public int getGreen()
    {
        return green;
    }
    public int getBlue()
    {
        return blue;
    }

    public void setRed(int red)
    {
        this.red = red;
    }
    public void setGreen(int green)
    {
        this.green = green;
    }
    public void setBlue(int blue)
    {
        this.blue = blue;
    }

    @Override
    public String toString()
    {
        return red + " " + green + " " + blue + "\n";
    }
}

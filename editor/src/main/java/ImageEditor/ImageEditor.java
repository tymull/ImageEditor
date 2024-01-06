package ImageEditor;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Ty on 9/12/2017.
 */

public class ImageEditor
{
    public static String usageMsg = new String("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
    public static int maxColor;

    public static void main(String[] args)
    {
        try
        {
            File myFile = new File(args[0]);
            Scanner s = new Scanner(myFile);
            s.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
            s.next(); //jump over P3
            int width = s.nextInt();
            int height = s.nextInt();
            maxColor = s.nextInt();
            Image image = new Image(height, width);
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++) //for each pixel
                {
                    int red = s.nextInt();
                    int green = s.nextInt();
                    int blue = s.nextInt();
                    image.createPixel(i, j, red, green, blue);
                    //image.setPixel(i, j, red, green, blue); //set each color
                }
            }

            PrintWriter pw = new PrintWriter(args[1]);

            //Now to decide what to do with the image
            if (args[2].equals("grayscale"))
            {
                image.grayScale();
            }
            else if (args[2].equals("invert"))
            {
                image.invert();
            }
            else if (args[2].equals("emboss"))
            {
                image.emboss();
            }
            else if (args[2].equals("motionblur"))
            {
                int blurLength = Integer.parseInt(args[3]);
                image.blur(blurLength);
            }
            else
            {
                throw new Exception(); //incorrect input
            }

            //now we will print the image
            pw.println("P3");
            pw.println(width);
            pw.println(height);
            pw.println(maxColor);
            pw.println(image.toString());
            pw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(usageMsg);
        }
    }
}

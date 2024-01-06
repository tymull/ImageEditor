package ImageEditor;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 * Created by Ty on 9/13/2017.
 */

public class Image
{
    private Pixel[][] image;
    private int height;
    private int width;

    public Image(int height, int width) //or Pixel[][] image, but this way keeps h and w to use
    {
        this.height = height;
        this.width = width;
        image = new Pixel[height][width];
    }

    public Pixel[][] getImage()
    {
        return image;
    }

    public void createPixel(int i, int j, int red, int green, int blue)
    {
        image[i][j] = new Pixel(red, green, blue);
    }

    public void setPixel(int i, int j, int red, int green, int blue)
    {
        image[i][j].setRed(red); //set each color to average
        image[i][j].setGreen(green);
        image[i][j].setBlue(blue);
    }

    public void invert()
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)//for each pixel in array
            {
                int red = ImageEditor.maxColor - image[i][j].getRed(); //gets inverse color
                int green = ImageEditor.maxColor - image[i][j].getGreen();
                int blue = ImageEditor.maxColor - image[i][j].getBlue();
                image[i][j].setRed(red); //reset each color
                image[i][j].setGreen(green);
                image[i][j].setBlue(blue);
            }
        }
    }

    public void grayScale()
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)//for each pixel in array
            {
                //this will average the colors together
                int gray = ((image[i][j].getRed() + image[i][j].getGreen() + image[i][j].getBlue()) / 3);
                image[i][j].setRed(gray); //reset each color to average
                image[i][j].setGreen(gray);
                image[i][j].setBlue(gray);
            }
        }
    }

    public void emboss()
    {
        for (int i = height - 1; i >= 0; i--) //I must start at bottom-right to preserve upper-left comparisons
        {
            for (int j = width - 1; j >=0; j--)
            {
                if (i != 0 && j != 0) //otherwise this would throw it out of bounds
                {
                    //calculate differences between each pixel's color with that of its color in pixel to upper-left
                    int redDiff = image[i][j].getRed() - image[i - 1][j - 1].getRed();
                    int greenDiff = image[i][j].getGreen() - image[i - 1][j - 1].getGreen();
                    int blueDiff = image[i][j].getBlue() - image[i - 1][j - 1].getBlue();
                    //now to find max difference giving priority to red, then green, then blue
                    int maxDiff = absMax(redDiff, greenDiff);
                    maxDiff = absMax(maxDiff, blueDiff);
                    int value = 128 + maxDiff;
                    if (value < 0) value = 0; //check to make sure within possible color range.
                    if (value > ImageEditor.maxColor) value = ImageEditor.maxColor;
                    image[i][j].setRed(value); //reset each color to new emboss value
                    image[i][j].setGreen(value);
                    image[i][j].setBlue(value);
                }
                else //if it is on the edge and has no pixel to its upper left, just set colors to 128
                {
                    image[i][j].setRed(128); //reset each color to new emboss value
                    image[i][j].setGreen(128);
                    image[i][j].setBlue(128);
                }
            }
        }
    }

    public int absMax(int a, int b)
    {
        if (Math.abs(b) > Math.abs(a)) //this will compare absolute and return with first int having high precidence
        {
            return b;
        }
        else
        {
            return a;
        }
    }

    public void blur(int blurLength)
    {
        for (int i = 0; i < height; i++)
        {

            //in case the blur length moves out of bounds
            for (int j = 0; j < width; j++)//for each pixel in array
            {
                int avgCnt = 1; //this counter will be used to determine how much to divide by to average
                int avgRed = image[i][j].getRed(); //initialize averages to current pixel
                int avgGreen = image[i][j].getGreen();
                int avgBlue = image[i][j].getBlue();
                int n = j + 1;//this will be used to increment pixels to average
                while (avgCnt < blurLength && n < width)//this will not execute if j is on right edge
                {
                    avgRed += image[i][n].getRed();
                    avgGreen += image[i][n].getGreen();
                    avgBlue += image[i][n].getBlue();
                    avgCnt++;
                    n++;
                }
                avgRed = avgRed / avgCnt;
                avgGreen = avgGreen / avgCnt;
                avgBlue = avgBlue / avgCnt;
                image[i][j].setRed(avgRed); //reset each color to new average value
                image[i][j].setGreen(avgGreen);
                image[i][j].setBlue(avgBlue);
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                output.append(image[i][j].toString());
            }
        }
        return output.toString();
    }
}

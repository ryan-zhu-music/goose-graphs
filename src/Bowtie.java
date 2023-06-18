// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: A bowtie that is collected by the goose

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bowtie {
    private Vector pos;
    private boolean obtained;
    private static int count = 0;
    private static ArrayList<Bowtie> bowties = new ArrayList<>();
    public static BufferedImage bowtie;

    public Bowtie(int x, int y) {
        this.pos = new Vector(x, y);
        this.obtained = false;
        bowties.add(this);

        try {
            bowtie = ImageIO.read(new File("bowtie.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    // resets all bowties to be redisplayed and unobtained
    public static void reset() {
        count = 0;
        for (Bowtie b : bowties) {
            b.obtained = false;
        }
    }

    public static int getCount() {
        return count;
    }

    // checks if the goose has collided with the bowtie
    public boolean checkCollision(Vector v) {
        if (pos.distance(v) < 30) {
            if (!obtained) {
                count++;
                obtained = true;
                Menu.honk.setFramePosition(0);
                Menu.honk.start();
            }
            return true;
        }
        return false;
    }

    // draws the bowtie if it has not been obtained
    public void draw(Graphics g2) {
        if (!obtained) {
            g2.drawImage(bowtie, (int) pos.getX() - 10, (int) pos.getY() - 10, null);
        }
    }

}

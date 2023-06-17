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

    public static void reset() {
        count = 0;
        for (Bowtie b : bowties) {
            b.obtained = false;
        }
    }

    public static int getCount() {
        return count;
    }

    public boolean checkCollision(Vector v) {
        if (pos.distance(v) < 30) {
            if (!obtained) {
                count++;
                obtained = true;
            }
            return true;
        }
        return false;
    }

    public void draw(Graphics g2) {
        if (!obtained) {
            g2.drawImage(bowtie, (int) pos.getX() - 10, (int) pos.getY() - 10, null);
            // g2.fillOval((int) pos.getX() - 10, (int) pos.getY() - 10, 20, 20);
        }
    }

}

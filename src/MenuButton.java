import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class MenuButton implements MouseListener, MouseMotionListener {
    public BufferedImage img;
    public BufferedImage img1;
    public int imgX, imgY, mouseX, mouseY;
    public boolean hovered = false;

    public MenuButton(String fileName, String fileName1, int imgX, int imgY) {
        try {
            this.img = ImageIO.read(new File(fileName));
            this.img1 = ImageIO.read(new File(fileName1));
            this.imgX = imgX;
            this.imgY = imgY;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println(":(");
        }
    }

    public void draw(Graphics g) {
        mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
        if (hovered) {
            g.drawImage(this.img1, this.imgX, this.imgY, null);
        } else {
            g.drawImage(this.img, this.imgX, this.imgY, null);
        }
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        System.out.println("clicked");
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX >= imgX && mouseX <= (imgX + 130) && mouseY >= imgY && mouseY <= (imgY + 134)) {
            hovered = true;
        } else {
            hovered = false;
        }
    }
}

package dmGame;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

// initializing the 'Enemy Car' class
class EnemyCar {
    double x, y;
    double speed;
    Image image;
    private static final Random rand = new Random();

    public EnemyCar(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
//        this.speed = 1.2 + rand.nextDouble() * 0.5; // speed between 1.2 and 2.7
        this.speed = 1.5;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 44, 92);
    }

    public void move() {
        y += speed;
    }
}

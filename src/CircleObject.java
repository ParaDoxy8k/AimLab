import java.awt.Graphics;
import java.util.Random;

public class CircleObject extends PointObject {

    private int w;
    private int h;
    private int radius;

    public CircleObject(int x, int y, int w, int h, int radius) {
        super(x, y);
        this.w = w;
        this.h = h;
        this.radius = radius;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public int getWidth() {
        return w;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public int getHeight() {
        return h;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
    //เมธอดนี้ใช้สุ่มค่า x และ y ใหม่ (จุดศูนย์กลาง) ให้กับวงกลม โดยอยู่ภายในขอบเขตที่ w และ h กำหนด
    public void resetPosition() {
        Random random = new Random();
        x = random.nextInt(w);
        y = random.nextInt(h);
    }
    //เมธอดที่น่าจะใช้ในการแสดงวงกลมบนหน้าจอ. โดยใช้ g.fillOval ของคลาส Graphics 
    //เพื่อวาดวงกลมรีที่มีจุดศูนย์กลางอยู่ที่พิกัด (x, y) และมีขนาดเป็นเส้นผ่านศูนย์กลางเป็นสองเท่าของรัศมี
    public void paintObject(Graphics g) {
        g.fillOval(x, y, radius * 2, radius * 2);
    }
    //เมธอดนี้จะตรวจสอบว่าพิกัด x และ y  ที่ได้รับนั้นอยู่ภายในวงกลมหรือไม่ 
    //โดยอาศัยการคำนวณระยะห่างแบบพีทากอรัส และเปรียบเทียบกับรัศมี
    public boolean contains(int x, int y) {
        int dx = x - this.x - radius;
        int dy = y - this.y - radius;
        int distance = dx * dx + dy * dy;
    
        return distance <= radius * radius;
    }
}
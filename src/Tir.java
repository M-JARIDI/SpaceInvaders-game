
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author mustapha
 */
public class Tir {
    
    protected int posX;
    protected int posY;
    private final int speed=10;
    protected final int size=6;
    
    public Tir(int posX, int posY)
    {
        this.posX=posX;
        this.posY=posY;
    }
    
    public void update()
    {
        if(this.posY>=0)
            this.posY-=speed;
    }
    
    public void affiche(GraphicsContext gc)
    {
       gc.setFill(Color.rgb(255,0,0));
        gc.fillOval(posX,posY,size,size);
    }
    
    public boolean collision(Alien alien){
        return (this.posX>=alien.posX && this.posX<=alien.posX+30)&&(this.posY<=alien.posY && this.posY>=alien.posY-30);
    }
    
}

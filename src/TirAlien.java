
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author mustapha
 */
public class TirAlien extends Tir{

    private final int speed=30;

    public TirAlien(int posX, int posY) {
        super(posX, posY);
    }

  
    @Override
    public void affiche(GraphicsContext gc){
        gc.setFill(Color.rgb(0,255,255));
        gc.fillOval(posX,posY,size/(size/4),3*size);
    }
    
    @Override
    public void update(){
        if(this.posY<=SpaceInvaders.HEIGHT)
            this.posY=this.posY+speed;  
    }
    
}

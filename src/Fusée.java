
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author mustapha
 */
class Fusée {
    
    protected int posX;
    protected int posY;
    protected final int size;
    protected boolean explosion, detruit;
    protected Image img;
    private final int SPEED_FUSEE=10;
    
    
    public Fusée(int posX, int posY, int size, Image img)
    {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img=img;
    }
    
    
    public Tir tir()
    {
        return new Tir(posX+10,posY-10);
    }
    
    public void affiche(GraphicsContext gc)
    {
        gc.drawImage(img, posX, posY, size, size);
    }
    
    
    
    public void moveRight()
    {
        if(this.posX <= (SpaceInvaders.WIDTH-SpaceInvaders.TAILLE_JOUEUR-SpaceInvaders.MARGE))
            this.posX+=SPEED_FUSEE;//faire bouger la fusée de 5 pixels      
    }
    
    public void moveLeft()//(GraphicsContext gc)
    {
        if(posX >= SpaceInvaders.MARGE)
            this.posX-=SPEED_FUSEE;
    }
    
    public boolean collision(Tir tir)
    {
        return (tir.posX>=this.posX && tir.posX<=this.posX+30)&&(this.posY<=tir.posY && tir.posY<=tir.posY+5);
    }

   

    
    
    
    
    
    
}

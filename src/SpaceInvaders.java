
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author mustapha
 */
public class SpaceInvaders extends Application{

    private Canvas canvas;
    private Pane root;
    private Scene scene;
    private GraphicsContext gc;
    private Fusée joueur;
    
    public static final int WIDTH=800;
    public static final int HEIGHT=600;
    public static final int MARGE=50;
    public static final int TAILLE_JOUEUR=80;
    public static final int TAILLE_ALIEN=70;
    public static final int NBR_ALIEN_LIGNE=6;
    public static int level=1;
    private int score=0;
    private Timeline timeline;
    private final Image JOUEUR_IMG = new Image("file:images\\joueur.png");
    private static final Image ALIEN_IMG = new Image("file:images/alien.png");
    private final Image EXPLOSION_IMG = new Image("file:images/explosion.png");
    public static ArrayList<ArrayList<Alien>> aliens = new ArrayList<ArrayList<Alien>>();
    public static ArrayList<Tir> tirs = new ArrayList<>();
    public static ArrayList<TirAlien> tirsAlien = new ArrayList<>();
    
  
   
    
    private void CreerContenu()
    {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        
        root = new Pane(canvas);
        root.setPrefSize(WIDTH, HEIGHT);
        
        scene = new Scene(root,WIDTH, HEIGHT);
        
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
       
        
      joueur = new Fusée(WIDTH/2 -35, HEIGHT - TAILLE_JOUEUR-10, TAILLE_JOUEUR, JOUEUR_IMG);
      joueur.affiche(gc); //affiche joueur
      
      ajouterAlien(level);
      for(int i=0; i<aliens.size(); i++){
            aliens.get(i).get(i).affiche(gc);
        }
            
       
       //mouvement du fusée
       scene.setOnKeyPressed(e -> {
        switch (e.getCode()) {
        case LEFT:
                joueur.moveLeft();
                break;
        case RIGHT:
                joueur.moveRight();
                break;
        case SPACE:
                tirs.add(joueur.tir());
                break;
        default:
                System.out.println("erreur");
        }
        });
       
       
       timeline = new Timeline(new KeyFrame(Duration.millis(100),e->{
        	run();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
  
    }
   
    
    public static void ajouterAlien(int level){
	int hauteur=90;
    	for(int i=0;i<level;i++)
        {
            int ecart=MARGE;
            aliens.add(new ArrayList<>());
            for(int j=0;j<NBR_ALIEN_LIGNE-4;j++)
            {
                aliens.get(i).add(new Alien(ecart,hauteur,TAILLE_ALIEN,ALIEN_IMG));
                ecart+=80;
            }
            hauteur+=TAILLE_ALIEN+10;    
        }
    }
    
    
    public static void descendre(){
        for(int i=0; i<aliens.size(); i++){
            for(int j=0; j<aliens.get(i).size(); j++){
            	aliens.get(i).get(j).posY=aliens.get(i).get(j).posY+Alien.vitesse;
            }
        }
    }

    private void run() {
        
        /*affiche le score et le niveau*/
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0,0,WIDTH,HEIGHT);
        gc.setFill(Color.rgb(255,0,255));
        gc.fillText("Score  : "+score,30,25);
        gc.setFill(Color.rgb(255,255,0));
        gc.fillText("Niveau : "+level,30,50);

        /*affiche les tirs du joueur*/
        for(int i=0;i<tirs.size();i++){
            if(tirs.get(i).posY>0){
                tirs.get(i).affiche(gc);
                tirs.get(i).update();
            }else{
                tirs.remove(tirs.get(i));
            }
        }
        
        /*affiche les tirs des aliens*/
        for(int i=0; i<tirsAlien.size();i++){
        	if(tirsAlien.get(i).posY<HEIGHT){
        		tirsAlien.get(i).affiche(gc);
        		tirsAlien.get(i).update();
        	}else{
        		tirsAlien.remove(tirsAlien.get(i));
        	}
        }

        /*en cas de collision tir_j et alien : affiche Explosion*/
        for(int i=0;i<tirs.size();i++)
            for(int j=0;j<aliens.size(); j++)
        	for(int k=0; k<aliens.get(j).size(); k++)
                    if(tirs.get(i).collision(aliens.get(j).get(k)))
                        {
                            gc.drawImage(EXPLOSION_IMG,aliens.get(j).get(k).posX,aliens.get(j).get(k).posY,50,50);
                            aliens.get(j).remove(aliens.get(j).get(k));
                            score=score+10;
                        }
                
            
        /*update des places des aliens*/
        for(int i=0;i<aliens.size();i++){
            for(int j=0; j<aliens.get(i).size(); j++){
                aliens.get(i).get(j).update();
            	aliens.get(i).get(j).affiche(gc);
            }
        }

        /*Game Over si les aliens arrivent au joueur*/
        for(int i=0;i<aliens.size();i++){
            for(int j=0; j<aliens.get(i).size(); j++){
                if(aliens.get(i).get(j).posY==joueur.posY){
                    gc.setFill(Color.grayRgb(20));
                    gc.fillRect(0,0,WIDTH,HEIGHT);
                    gc.setFill(Color.rgb(255,255,255));
                    gc.fillText("GAME OVER - LA PARTIE EST TERMINEE",WIDTH/2-100,HEIGHT/2);
                    timeline.stop();
                }
            }
        }
        
        /*si les tirs des aliens touchent le joueur */
        for(int i=0; i<tirsAlien.size();i++){
        	if(joueur.collision(tirsAlien.get(i))){
                gc.setFill(Color.rgb(255,255,255));
                gc.fillText("GAME OVER - LA PARTIE EST TERMINEE",WIDTH/2-100,HEIGHT/2);
                gc.setFill(Color.rgb(255,0,0));
                timeline.stop();
        	}
        }
        
        joueur.affiche(gc);
        
        /*---------- si le joueur tue tous les aliens, passe au 2ème level.*/
        int k=0;
        for(int i=0; i<aliens.size(); i++)
            if(aliens.get(i).isEmpty())
                k++;
            
        if(k==aliens.size())
        {
            level++;
            score=score+1000;
            ajouterAlien(level);
        }
        /*-------------------------------------------*/
    }
    
    
    
    @Override
    public void start(Stage stage)
    {
        CreerContenu();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Space Invaders");
    }


public static void main(String[] args) {
        
        launch(args); //obligatoire, cette methode appelera la methode start() implementé dans la classe
    }


}
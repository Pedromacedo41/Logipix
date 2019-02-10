import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException; 
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.lang.Object;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GameInterface extends Application {

    int square=26;
    int cont=0;
    static String name;
    static int option;
    double BackTrackingtime;

    @Override
    public void start(Stage stage) {
        Logipix logipix = new Logipix();
        String titulo;
        logipix.initialize(titulo=("InputFiles/"+name+".txt"));
        long iniTime = System.nanoTime();
        if(option==1) logipix.Backtracking(option);
        if(option==3) logipix.Backtracking2();
        //logipix.example();
        //logipix.pre_processing();
        long endTime = System.nanoTime();

        BackTrackingtime = (endTime - iniTime)/(1.0*1000000000);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        int X= logipix.sizeX*square, Y = logipix.sizeY*square;
        if(X>width){
            square=(int) 85*square*width/X/100;
        }
        if(Y>height){
            square=(int) 85*square*height/Y/100;
        }

        Scene scene = new Scene(generateGrid(logipix), logipix.sizeX*square, logipix.sizeY*square+12);
        stage.setTitle("Logipix "+logipix.sizeX+"x"+logipix.sizeY+ "  "+titulo);
        stage.setScene(scene);


        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        name = args[0];
        option = Integer.parseInt(args[1]);

        launch();
    }

    private GridPane generateGrid(Logipix logipix){
        Text T1 = new Text("Pre-processing(s) : "+ logipix.pre_processing_time+ "  ");
        T1.setStyle("-fx-font: "+ 10+" arial; -fx-padding: 10;");

        Text T2 = new Text("BackTracking returns: "+logipix.a);
        T2.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        T2.setFill(Color.GREEN);

        Text T3 = new Text( "BackTracking Runtime: " + BackTrackingtime+ "s   ");
        T3.setStyle("-fx-font: "+ 10+" arial;");
        T3.setFill(Color.BLUE);

        HBox j=  new HBox(T1,T3,T2);
        VBox m = new VBox();
        m.getChildren().add(j);
        m.getChildren().add(draw(logipix)); 
        GridPane gridpane = new GridPane();
        gridpane.add(m,0,0);
        return gridpane;
    }

    private int cont(){
        return cont++;
    }

    private HBox draw(Logipix logipix){ 
        VBox m = new VBox();

        for(int i=0; i < logipix.sizeY; i++){
            HBox b = new HBox();
            for (int j=0; j< logipix.sizeX ; j++) {
                StackPane stack = new StackPane();
                Text T;
                if(logipix.GameGrid[i][j].clue==0){
                    T= new Text(" ");
                }else{
                    T= new Text(Integer.toString(logipix.GameGrid[i][j].clue));
                    T.setStyle("-fx-font: "+ 12*square/26+" arial;");
                }
                 
                T.setTextAlignment(TextAlignment.CENTER);

                Rectangle a = new Rectangle(square,square);
                a.setStrokeWidth(0.8);
                a.setStrokeType(StrokeType.INSIDE);
                a.setStroke(Color.BLACK);
                a.setFill(Color.WHITE);

                if(logipix.GameGrid[i][j].linked) a.setFill(Color.RED);
                //if(logipix.GameGrid[i][j].mandatory_1!=null || logipix.GameGrid[i][j].mandatory_2!=null) a.setFill(Color.YELLOW);
                //if(logipix.GameGrid[i][j].is_irreversible) a.setFill(Color.RED);

                if(logipix.GameGrid[i][j].linked){
                    
                    //one voisin
                    if((logipix.GameGrid[i][j].pos1==Position.EMPTY || logipix.GameGrid[i][j].pos2==Position.EMPTY)) {
                        if(logipix.GameGrid[i][j].clue==1){
                                MoveTo moveTo = new MoveTo(7,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5+6,a.getHeight()*0.5);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.OUTSIDE);
                                path.setStrokeWidth(1.5);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                        }else{
                            if(logipix.GameGrid[i][j].pos1==Position.LEFT || logipix.GameGrid[i][j].pos2==Position.LEFT){
                                MoveTo moveTo = new MoveTo(a.getHeight()*0.5+6,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(2.3,a.getHeight()*0.5);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.OUTSIDE);
                                path.setStrokeWidth(1.5);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                            if(logipix.GameGrid[i][j].pos1==Position.RIGHT || logipix.GameGrid[i][j].pos2==Position.RIGHT){
                                MoveTo moveTo = new MoveTo(7,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(a.getHeight()-2.3,a.getHeight()*0.5);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.OUTSIDE);
                                path.setStrokeWidth(1.5);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                            if(logipix.GameGrid[i][j].pos1==Position.UP || logipix.GameGrid[i][j].pos2==Position.UP){
                                MoveTo moveTo = new MoveTo(a.getHeight()*0.5,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,2.3);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.OUTSIDE);
                                path.setStrokeWidth(1.5);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                            if(logipix.GameGrid[i][j].pos1==Position.DOWN || logipix.GameGrid[i][j].pos2==Position.DOWN){
                                MoveTo moveTo = new MoveTo(a.getHeight()*0.5,a.getHeight()*0.5); 
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,a.getHeight()-2.3);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.OUTSIDE);
                                path.setStrokeWidth(1.5);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                        }
                    }else{
                        //two voisins
                            if((logipix.GameGrid[i][j].pos1==Position.DOWN && logipix.GameGrid[i][j].pos2==Position.RIGHT) 
                                || (logipix.GameGrid[i][j].pos2==Position.DOWN && logipix.GameGrid[i][j].pos1==Position.RIGHT)){

                                MoveTo moveTo = new MoveTo(a.getHeight()*0.5,a.getHeight()-2.3);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,a.getHeight()*0.5);
                                LineTo lineTo2 = new LineTo(a.getHeight()-2.3,a.getHeight()*0.5);
                                Path path = new Path(moveTo,lineTo,lineTo2);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.CENTERED);
                                path.setStrokeWidth(2.8);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                            if((logipix.GameGrid[i][j].pos1==Position.DOWN && logipix.GameGrid[i][j].pos2==Position.UP) 
                                || (logipix.GameGrid[i][j].pos2==Position.DOWN && logipix.GameGrid[i][j].pos1==Position.UP)){
                               
                                MoveTo moveTo = new MoveTo(a.getHeight()*0.5,2.3);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,a.getHeight()-2.3);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.CENTERED);
                                path.setStrokeWidth(2.8);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                            if((logipix.GameGrid[i][j].pos1==Position.DOWN && logipix.GameGrid[i][j].pos2==Position.LEFT) 
                                || (logipix.GameGrid[i][j].pos2==Position.DOWN && logipix.GameGrid[i][j].pos1==Position.LEFT)){

                                MoveTo moveTo = new MoveTo(2.3,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,a.getHeight()*0.5);
                                LineTo lineTo2 = new LineTo(a.getHeight()*0.5,a.getHeight()-2.3);
                                Path path = new Path(moveTo,lineTo,lineTo2);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.CENTERED);
                                path.setStrokeWidth(2.8);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                                
                            }
                            if((logipix.GameGrid[i][j].pos1==Position.RIGHT && logipix.GameGrid[i][j].pos2==Position.LEFT) 
                                || (logipix.GameGrid[i][j].pos2==Position.RIGHT && logipix.GameGrid[i][j].pos1==Position.LEFT)){

                                MoveTo moveTo = new MoveTo(2.3,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(a.getHeight()-2.3,a.getHeight()*0.5);
                                Path path = new Path(moveTo,lineTo);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.CENTERED);
                                path.setStrokeWidth(2.8);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                                
                            }
                            if((logipix.GameGrid[i][j].pos1==Position.UP && logipix.GameGrid[i][j].pos2==Position.RIGHT) 
                                || (logipix.GameGrid[i][j].pos2==Position.UP && logipix.GameGrid[i][j].pos1==Position.RIGHT)){

                                MoveTo moveTo = new MoveTo(a.getHeight()*0.5,2.3);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,a.getHeight()*0.5);
                                LineTo lineTo2 = new LineTo(a.getHeight()-2.3,a.getHeight()*0.5);
                                Path path = new Path(moveTo,lineTo,lineTo2);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.CENTERED);
                                path.setStrokeWidth(2.8);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                                
                            }
                            if((logipix.GameGrid[i][j].pos1==Position.UP && logipix.GameGrid[i][j].pos2==Position.LEFT) 
                                || (logipix.GameGrid[i][j].pos2==Position.UP && logipix.GameGrid[i][j].pos1==Position.LEFT)){

                                MoveTo moveTo = new MoveTo(2.3,a.getHeight()*0.5);
                                LineTo lineTo = new LineTo(a.getHeight()*0.5,a.getHeight()*0.5); 
                                LineTo lineTo2 = new LineTo(a.getHeight()*0.5,2.3); 
                                Path path = new Path(moveTo,lineTo,lineTo2);
                                path.setStroke(Color.GREEN);
                                path.setStrokeType(StrokeType.CENTERED);
                                path.setStrokeWidth(2.8);
                                Pane pane = new Pane(a,path);
                                stack.getChildren().add(pane);
                                stack.getChildren().add(T);
                            }
                    }
                }else{
                    stack.getChildren().add(a);
                    stack.getChildren().add(T);
                }     
                b.getChildren().add(stack);
            }
            m.getChildren().add(b);
        }

        return new HBox(m);
    }

}
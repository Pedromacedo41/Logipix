import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException; 
import java.io.IOException;

public class GameInterface extends Application {

    int square=26;

    @Override
    public void start(Stage stage) {
        Logipix logipix = new Logipix();
        logipix.initialize("InputFiles/Man.txt");
        //logipix.example();
        Scene scene = new Scene(generateGrid(logipix), logipix.sizeX*square, logipix.sizeY*square);
        stage.setTitle("Logipix "+logipix.sizeX+"x"+logipix.sizeY);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch();
    }

    private StackPane generateGrid(Logipix logipix){
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
                }
                 
                T.setTextAlignment(TextAlignment.CENTER);

                Rectangle a = new Rectangle(square,square);
                a.setStrokeWidth(0.8);
                a.setStrokeType(StrokeType.INSIDE);
                a.setStroke(Color.BLACK);
                a.setFill(Color.WHITE);

                if(logipix.GameGrid[i][j].linked) a.setFill(Color.AQUAMARINE);

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
                                || (logipix.GameGrid[i][j].pos2==Position.DOWN && logipix.GameGrid[i][j].pos1==Position.LEFT)){

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
        StackPane prin = new StackPane(m);
        return prin;
    }

}
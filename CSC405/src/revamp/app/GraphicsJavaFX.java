package revamp.app;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import revamp.unused.MatrixOpSG;
import revamp.basetypes.*;
import revamp.operations.MatrixOp;


public class GraphicsJavaFX extends Application
{
    int WIDTH = 500;
    int HEIGHT = 500;
    Scene mainScene;
    // -- Main container
    BorderPane pane;
    // -- Graphics container
    GraphicsCanvasInner graphicsCanvas;
    // -- Controls container
    ControlBoxInner controlBox;
    Stage mainStage;
    Shape shape;
    AnimationTimer animationTimer;

    @Override
    public void start(Stage mainStage)
    {
        this.mainStage = mainStage;
        // -- Application title
        mainStage.setTitle("Assignment 5 Java FX");
        // -- create canvas for drawing
        graphicsCanvas = new GraphicsCanvasInner(WIDTH, HEIGHT);
        // -- construct the controls
        controlBox = new ControlBoxInner();
        // -- create the primary window structure
        pane = new BorderPane();
        // -- add the graphics canvas and the control box to the split pan
        pane.setLeft(controlBox);
        pane.setCenter(graphicsCanvas);
        // -- set up key listeners (to Pane)
        prepareActionHandlers(pane);
        mainScene = new Scene(pane);
        mainStage.setScene(mainScene);
        // -- paint the graphics canvas before the initial display
        graphicsCanvas.repaint();
        // set up graph
        shape = new Shape();
        // -- display the application window
        mainStage.show();
        // -- set keyboard focus to the pane
        pane.requestFocus();
    }

    // -- key handlers belong to the Pane
    private void prepareActionHandlers(Pane container)
    {
        // -- key listeners belong to Pane
        container.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode().toString());
                graphicsCanvas.repaint();
            }
        });
        container.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                graphicsCanvas.repaint();
            }
        });
    }
    // -- launch the application
    public void launchApp(String[] args)
    {
        launch(args);
    }
    // -- Inner class for Graphics
    public class GraphicsCanvasInner extends Canvas  {
        private GraphicsContext graphicsContext;
        private RenderSurface renderSurface;

        public GraphicsCanvasInner(int width, int height)
        {
            super(width, height);
            // -- get the context for drawing on the canvas
            graphicsContext = this.getGraphicsContext2D();
            // -- set up event handlers for mouse
            prepareActionHandlers();
            renderSurface = new RenderSurface((int)width, (int)height);
        }
        // -- check the active keys and render graphics
        public void repaint()
        {
            graphicsCanvas.renderSurface.insertArray();
            double height = this.getHeight();
            double width = this.getWidth();
            // -- clear canvas
            graphicsContext.clearRect(0, 0, width, height);
            graphicsContext.setStroke(Color.RED);
            graphicsContext.drawImage(renderSurface, 0, 0, this.getWidth(), this.getHeight());
        }

        private void prepareActionHandlers()
        {
            // -- mouse listeners belong to the canvas
            this.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                    }
                    else if (event.getButton() == MouseButton.SECONDARY) {
                    }
                    pane.requestFocus();
                }
            });
            this.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                    }
                    else if (event.getButton() == MouseButton.SECONDARY) {
                    }
                    pane.requestFocus();
                    repaint();
                }
            });
            this.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                    }
                    else if (event.getButton() == MouseButton.SECONDARY) {
                    }
                    pane.requestFocus();
                    repaint();
                }
            });
        }
    }


    // -- Inner class for Controls
    public class ControlBoxInner extends VBox {
        private Button render;
        private Button translate;
        private Button scale;
        private Button rotate;

        private Button reset;
        private Button SurfaceNorms;
        //private boolean toggle = false;
        private Button savePNG;

        private TextField trans_amt;
        private TextField scale_amt;
        private TextField degree_amt;
        private TextField degree;
        private FileChooser fileChooser;

        public ControlBoxInner()
        {
            super();
            // set up for saving
            fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", ".png");
            fileChooser.getExtensionFilters().add(extFilter);
            // fixedPoint = new TextField("0,0,0");
            //fixedPoint.setMaxWidth(100);
            trans_amt = new TextField("0,0,0");
            trans_amt.setMaxWidth(100);
            scale_amt = new TextField("0,0,0");
            scale_amt.setMaxWidth(100);
            degree_amt = new TextField("0,0,0");
            degree_amt.setMaxWidth(100);
            degree = new TextField("45");
            degree.setMaxWidth(60);

            // -- set up buttons
            //this.setSpacing(5);
            prepareButtonHandlers();
            this.getChildren().add(new Label(" Refresh Scene:"));
            this.getChildren().add(render);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(new Label(" Edit Scene:"));
            this.getChildren().add(translate);
            //this.getChildren().add(new Label("(x, y, z)"));
            this.getChildren().add(trans_amt);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(scale);
            // this.getChildren().add(new Label("(x, y, z)"));
            this.getChildren().add(scale_amt);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(rotate);
            this.getChildren().add(degree_amt);
            this.getChildren().add(degree);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(new Label(" Misc:"));
            this.getChildren().add(reset);
            this.getChildren().add(SurfaceNorms);
            this.getChildren().add(savePNG);

//            animationTimer = new AnimationTimer() {
//                public void handle(long currentNanoTime) {
//                    graphicsCanvas.renderSurface.clear();
//                    MatrixOp.rotateZInPlace(shape, Double.parseDouble(degree.getText()));
//                    shape.render(graphicsCanvas.renderSurface.getSurface());
//                    graphicsCanvas.repaint();
//                    pane.requestFocus();
//                }
//            };
        }

        private void prepareButtonHandlers()
        {
            // graphic
            render = new Button();
            render.setText("Render");
            render.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    graphicsCanvas.renderSurface.clear();
                    shape.render(graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.repaint();
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });

            // translate
            translate = new Button();
            translate.setText("Translate");
            translate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    shape.fixedpoint = convertToPoint(trans_amt);
                    MatrixOp.translate(shape, convertToPoint(trans_amt));
                    //  fixedPoint.setText(graph.fixedPoint[0] +"," + graph.fixedPoint[1] +","+graph.fixedPoint[2]);
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });

            // scale
            scale = new Button();
            scale.setText("Scale");
            scale.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    MatrixOp.scaleInPlace(shape, convertToPoint(scale_amt));
                    //   fixedPoint.setText(graph.fixedPoint[0] +"," + graph.fixedPoint[1] +","+graph.fixedPoint[2]);
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });

            // rotate X
            rotate = new Button();
            rotate.setText("Rotate");
            rotate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    MatrixOp.rotateArb(shape, convertToPoint(degree_amt), Double.parseDouble(degree.getText()));
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });

            // Reset
            reset = new Button();
            reset.setText("Reset");
            reset.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    shape.reset();
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });
            // Prints surfaceNorms
            SurfaceNorms = new Button();
            SurfaceNorms.setText("Print SurfaceNorms");
            SurfaceNorms.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    System.out.println("-----------------------------------------------------");
                    for(int i = 0; i < shape.numOfFaces(); i++)
                    {
                        double[] cur = shape.getSurfaceNorm(i);
                        System.out.println("SN" +(i+1)+ ": "+cur[0] +", "+cur[1]+", "+cur[2]);
                    }
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });
            //Save As PNG
            savePNG = new Button();
            savePNG.setText("Save as PNG");
            savePNG.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    //Show save file dialog
                    File file = fileChooser.showSaveDialog(mainStage);
                    if (file != null) {
                        //fileChooser.saveFile(graphicsCanvas.renderSurface.toImage(), file);
                        try {
                            ImageIO.write(graphicsCanvas.renderSurface.toImage(), "png", file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });
        }
        /////////////////////
        private double[] convertToPoint(TextField text)
        {
            double[] result = new double[4];
            String[] textBox = text.getText().split(",");
            for(int i = 0; i < textBox.length; i++)
            {
                result[i] = Double.parseDouble(textBox[i]);
            }
            result[3] = shape.fixedpoint[3];
            return result;
        }
    }
}


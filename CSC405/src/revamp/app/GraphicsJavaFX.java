package revamp.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.util.Pair;
import revamp.coreTypes.*;
import revamp.operations.BaseOp;
import revamp.operations.TransformOp;


public class GraphicsJavaFX extends Application
{
    int WIDTH = 900;
    int HEIGHT = 900;
    Scene mainScene;
    // -- Main container
    BorderPane pane;
    // -- Graphics container
    GraphicsCanvasInner graphicsCanvas;
    // -- Controls container
    ControlBoxInner controlBox;
    Stage mainStage;
    ArrayList<Shape> shapes;
    ArrayList<Pair<Double,Double>> clickpoints;
    private CheckBox createShapes;
    private Pair<Double,Double> selcPoint;

    @Override
    public void start(Stage mainStage)
    {
        this.mainStage = mainStage;
        // -- Application title
        mainStage.setTitle("Graphics - Java FX");
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
        shapes = new ArrayList<>();
        clickpoints = new ArrayList<>();
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
                    if(createShapes.isSelected()){
                        if (event.getButton() == MouseButton.PRIMARY) {
                            clickpoints.add(new Pair<Double,Double>(event.getX(), event.getY()));
                            //System.out.println(event.getX()+","+ event.getY());
                        }
                        else if (event.getButton() == MouseButton.SECONDARY) {
                            if (clickpoints.size() >= 3) {
                                double[][] tempShape = new double[4][clickpoints.size()];
                                for (int i = 0; i < clickpoints.size(); i++) {
                                    tempShape[0][i] = clickpoints.get(i).getKey();
                                    tempShape[1][i] = clickpoints.get(i).getValue();
                                    tempShape[2][i] = 1.0;
                                    tempShape[3][i] = 1.0;
                                }
                                shapes.add(new Shape(tempShape));
                                System.out.println("Shape Added/ size ->"+ shapes.size());
                                clickpoints.clear();

                                renderSurface.clear();
                                for(Shape shape: shapes) {
                                    ColorT[][] res = shape.render(renderSurface.empty());
                                    renderSurface.merge(res);
                                }
                                repaint();
                            }
                        }
                    }
                    else {
                        clickpoints.clear();
                        if (event.getButton() == MouseButton.PRIMARY) {
                            selcPoint = new Pair<Double,Double>(event.getX(), event.getY());
                            System.out.println("Selected");
                            int closestShape = -1;
                            double dist = Double.MAX_VALUE;
                            for(int i = 0; i < shapes.size(); i++){
                                double curDist = BaseOp.distance(selcPoint.getKey(),selcPoint.getValue(),shapes.get(i).fixedpoint[0],shapes.get(i).fixedpoint[1]);
                                if(dist > curDist){
                                    dist = curDist;
                                    closestShape = i;
                                }
                            }
                            Collections.swap(shapes,closestShape,0);
                        }
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
        private Button change_color;
        private Button translate;
        private Button scale;
        private Button rotate;
        private Button render_cube;

        private Button reset;
        private Button savePNG;

        private TextField trans_amt_x;
        private TextField trans_amt_y;
        private TextField trans_amt_z;

        private TextField scale_amt_x;
        private TextField scale_amt_y;
        private TextField scale_amt_z;

        private TextField color_r;
        private TextField color_b;
        private TextField color_g;

        private TextField degree_amt_x;
        private TextField degree_amt_y;
        private TextField degree_amt_z;
        private TextField degree;

        private FileChooser fileChooser;

        public ControlBoxInner()
        {
            super();
            // set up for saving
            fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", ".png");
            fileChooser.getExtensionFilters().add(extFilter);
            createShapes = new CheckBox("Create Shape");
            createShapes.setSelected(true);

            // fixedPoint = new TextField("0,0,0");
            //fixedPoint.setMaxWidth(100);
            trans_amt_x = new TextField("x");
            trans_amt_x.setMaxWidth(50);
            trans_amt_y = new TextField("y");
            trans_amt_y.setMaxWidth(50);
            trans_amt_z = new TextField("z");
            trans_amt_z.setMaxWidth(50);

            scale_amt_x = new TextField("x");
            scale_amt_x.setMaxWidth(50);
            scale_amt_y = new TextField("y");
            scale_amt_y.setMaxWidth(50);
            scale_amt_z = new TextField("z");
            scale_amt_z.setMaxWidth(50);

            degree_amt_x = new TextField("x");
            degree_amt_x.setMaxWidth(50);
            degree_amt_y = new TextField("y");
            degree_amt_y.setMaxWidth(50);
            degree_amt_z = new TextField("z");
            degree_amt_z.setMaxWidth(50);
            color_r = new TextField("r");
            color_r.setMaxWidth(50);
            color_g = new TextField("g");
            color_g.setMaxWidth(50);
            color_b = new TextField("b");
            color_b.setMaxWidth(50);
            degree = new TextField("0");
            degree.setMaxWidth(50);
            // -- set up buttons
            //this.setSpacing(5);
            prepareButtonHandlers();
            this.getChildren().add(new Label(" Add Shapes:"));
            this.getChildren().add(render_cube);
            this.getChildren().add(createShapes);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(new Label(" Edit Shapes:"));
            this.getChildren().add(translate);
            //this.getChildren().add(new Label("(x, y, z)"));
            this.getChildren().add(trans_amt_x);
            this.getChildren().add(trans_amt_y);
            this.getChildren().add(trans_amt_z);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(scale);
            // this.getChildren().add(new Label("(x, y, z)"));
            this.getChildren().add(scale_amt_x);
            this.getChildren().add(scale_amt_y);
            this.getChildren().add(scale_amt_z);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(rotate);
            this.getChildren().add(degree_amt_x);
            this.getChildren().add(degree_amt_y);
            this.getChildren().add(degree_amt_z);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(new Label(" Degrees"));
            this.getChildren().add(degree);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(new Label(" Color"));
            this.getChildren().add(change_color);
            this.getChildren().add(color_r);
            this.getChildren().add(color_g);
            this.getChildren().add(color_b);
            this.getChildren().add(new Label(" "));
            this.getChildren().add(new Label(" Misc:"));
            this.getChildren().add(reset);
            this.getChildren().add(savePNG);
        }

        private void prepareButtonHandlers()
        {
            // graphic cube
            change_color = new Button();
            change_color.setText("Change Color");
            change_color.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    try {
                        shapes.get(shapes.size() - 1).setColor(new ColorT(Integer.parseInt(color_r.getText()),
                                Integer.parseInt(color_g.getText()),
                                Integer.parseInt(color_b.getText())));
                    }
                    catch (NumberFormatException ex)
                    {
                        shapes.get(shapes.size() - 1).setColor(new ColorT(255,255,255));
                    }
                    // -- and return focus back to the pane
                    renderScene();
                    pane.requestFocus();
                }
            });

            render_cube = new Button();
            render_cube.setText("Add Cube");
            render_cube.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    shapes.add(new Shape());
                    renderScene();
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
                    shapes.get(0).fixedpoint = convertToPoint(trans_amt_x, trans_amt_y, trans_amt_z);
                    TransformOp.translate(shapes.get(0), shapes.get(0).fixedpoint);
                    renderScene();
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
                    TransformOp.scaleInPlace(shapes.get(0), convertToPoint(scale_amt_x, scale_amt_y, scale_amt_z));
                    renderScene();
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
                    double d;
                    try {
                        d = Double.parseDouble(degree.getText());
                    }
                    catch (NumberFormatException ex)
                    {
                        d = 0.0;
                    }
                    TransformOp.rotateArb(shapes.get(0), convertToPoint(degree_amt_x, degree_amt_y, degree_amt_z),d);
                    renderScene();
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });

            // Reset
            reset = new Button();
            reset.setText("Clear");
            reset.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    shapes = new ArrayList<>();
                    renderScene();
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

        private double[] convertToPoint(TextField x, TextField y, TextField z)
        {
            double[] tryThis = new double[4];
            TextField[] text = new TextField[]{x,y,z};

            for(int i = 0; i < text.length; i++)
            {
                try {
                    tryThis[i] = Double.parseDouble(text[i].getText());
                }
                // if you can't parse it, make it a zero
                catch(NumberFormatException e) {
                    tryThis[i] = 0;
                }
            }

            tryThis[3] = 1.0;
            return tryThis;
        }

        public void renderScene()
        {
            graphicsCanvas.renderSurface.clear();
            for(Shape shape: shapes) {
                ColorT[][] res = shape.render(graphicsCanvas.renderSurface.empty());
                graphicsCanvas.renderSurface.merge(res);
            }
            graphicsCanvas.repaint();
        }
    }
}

package revamp;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
    SceneGraph graph;

    @Override
    public void start(Stage mainStage)
    {
        this.mainStage = mainStage;
    	// -- Application title
        mainStage.setTitle("Assignment 3 Java FX");
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
        graph = new SceneGraph();
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
        private Button Create_Box;
        //private Button bresen_Test;
        private Button savePNG;

        private FileChooser fileChooser;

        public ControlBoxInner()
        {
            super();
            // set up for saving
            fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", ".png");
            fileChooser.getExtensionFilters().add(extFilter);

            // -- set up buttons
            prepareButtonHandlers();
            this.getChildren().add(Create_Box);
            //this.getChildren().add(bresen_Test);
            this.getChildren().add(savePNG);
        }

        private void prepareButtonHandlers()
        {
            // Box
            Create_Box = new Button();
            Create_Box.setText("Scene");
            Create_Box.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // -- process the button
                    graphicsCanvas.renderSurface.clear();
                    graph.render(graphicsCanvas.renderSurface.getSurface());
                    graphicsCanvas.repaint();
                    // -- and return focus back to the pane
                    pane.requestFocus();
                }
            });
//            // bresen test
//            bresen_Test = new Button();
//            bresen_Test.setText("Bresenham Test");
//            bresen_Test.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    // -- process the button
//                    graphicsCanvas.renderSurface.clear();
//                    Line.star(graphicsCanvas.renderSurface.getSurface());
//                    graphicsCanvas.repaint();
//                    // -- and return focus back to the pane
//                    pane.requestFocus();
//                }
//            });
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
        }
    }


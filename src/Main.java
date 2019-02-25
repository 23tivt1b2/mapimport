import Map.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Map map = null;
        try {
            map = new Map("/map.json");
            map.getwidth();
        } catch (Exception e) {
            System.out.println("error");
        }

    }

}

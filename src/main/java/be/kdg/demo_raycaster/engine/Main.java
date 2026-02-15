package be.kdg.demo_raycaster.engine;
import be.kdg.demo_raycaster.engine.presenter.GamePresenter;
import be.kdg.demo_raycaster.engine.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage)
    {
        GameView view = new GameView();
        GamePresenter presenter = new GamePresenter(view);

        stage.setTitle("Raycasting");
        stage.setScene(view.getScene());
        stage.show();

        presenter.startGameLoop();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

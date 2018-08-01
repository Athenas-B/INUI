package cz.upce.inui.dreamteam;

import cz.upce.inui.dreamteam.service.CityFactory;
import cz.upce.inui.dreamteam.service.GuardedCityFinder;
import cz.upce.inui.dreamteam.state.City;
import cz.upce.inui.dreamteam.state.Position;
import cz.upce.inui.dreamteam.state.Station;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class GameWindowController implements Initializable {

    private static final Font ARIEL = Font.font("Ariel", FontWeight.BOLD, 14);
    private final static int SIZE = 30, OFFSET_X = 10, OFFSET_Y = 20;

    @FXML
    private Pane gamePane;
    private City city;
    private Set<Station> selectedStations;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        city = CityFactory.instance().getEmptyCity();
        selectedStations = new HashSet<>();
        this.drawCity();
    }

    private void drawCity() {
        gamePane.getChildren().clear();
        this.drawStreets();
        this.drawStations();
    }

    private void drawStreets() {
        city.getStreets().forEach(street -> {
            Position firstEnd = street.getFirstEnd();
            Position secondEnd = street.getSecondEnd();
            Line gStreet = new Line(firstEnd.getX() + OFFSET_X, firstEnd.getY() + OFFSET_Y, secondEnd.getX() + OFFSET_X, secondEnd.getY() + OFFSET_Y);
            gStreet.setStrokeWidth(SIZE / 2);
            gStreet.setStroke(street.isGuarded() ? Color.GREEN : Color.RED);
            gamePane.getChildren().add(gStreet);
        });

    }

    private void drawStations() {
        city.getStations().forEach((station) -> {
            Position position = station.getPosition();
            int x = position.getX();
            int y = position.getY();

            Rectangle gStation = new Rectangle(x, y, SIZE, SIZE);
            gStation.setFill(station.isGuarded() ? Color.GREENYELLOW : Color.WHITE);
            gStation.setStroke(Color.BLACK);
            Text code = new Text(x + OFFSET_X, y + OFFSET_Y, station.getCode());
            code.setFont(ARIEL);
            Group group = new Group(gStation, code);
            group.setOnMouseClicked(handleStationClick(station));
            gamePane.getChildren().add(group);
        });
    }

    private EventHandler<MouseEvent> handleStationClick(Station station) {
        return event -> {
            if (selectedStations.contains(station)) {
                selectedStations.remove(station);
                city.leaveStation(station);
                drawCity();
            } else if (selectedStations.size() < 3) {
                selectedStations.add(station);
                city.guardStation(station);
                drawCity();
            }
        };
    }

    @FXML
    public void findSolution() {
        city = new GuardedCityFinder().findGuardedCity();
        List<Station> guardedStations = city.getStations().stream().filter(Station::isGuarded).collect(Collectors.toList());
        selectedStations.clear();
        selectedStations.addAll(guardedStations);
        drawCity();
    }
}

package markov;

import markov.map.Location;
import markov.map.Position;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SimulationLocation extends JPanel {
    public Location location;

    public SimulationLocation(Position position, Location location, double normalizedCost) {
        this.location = location;
        if (location == Location.START) {
            setName(String.format("Position(%s,%s) - START", position.getX(), position.getY()));
        } else if (location == Location.DESTINATION) {
            setName(String.format("Position(%s,%s) - DESTINATION", position.getX(), position.getY()));
        } else {
            setName(String.format("Position(%s,%s)", position.getX(), position.getY()));
        }
        setPreferredSize(new Dimension(60, 60));
        setBorder(new LineBorder(Color.GRAY, 1));
        int red = (int) (normalizedCost * 255);
        int green = (int) ((1 - normalizedCost) * 255);
        setBackground(new Color(red, green, 0));
    }
}

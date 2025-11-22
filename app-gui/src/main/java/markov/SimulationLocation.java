package markov;

import markov.map.Location;
import markov.map.Position;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class SimulationLocation extends JPanel {
    public Location location;

    public SimulationLocation(Position position, boolean isCurrentPosition, Location location, double normalizedCost) {
        this.location = location;
        if (location == Location.START) {
            setName(String.format("Position(%s,%s) - START", position.getX(), position.getY()));
        } else if (location == Location.DESTINATION) {
            setName(String.format("Position(%s,%s) - DESTINATION", position.getX(), position.getY()));
        } else {
            setName(String.format("Position(%s,%s)", position.getX(), position.getY()));
        }
        if (isCurrentPosition) {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/crong.png")));
            Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaled);
            JLabel label = new JLabel(resizedIcon);
            label.setName("Current");
            add(label);
        }
        setPreferredSize(new Dimension(60, 60));
        setBorder(new LineBorder(Color.GRAY, 1));
        int red = (int) (normalizedCost * 255);
        int green = (int) ((1 - normalizedCost) * 255);
        setBackground(new Color(red, green, 0));
    }
}

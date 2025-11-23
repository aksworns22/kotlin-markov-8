package gui;

import markov.map.Location;
import markov.map.Position;
import markov.movement.ActionType;
import markov.movement.Action;
import markov.movement.Probability;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class SimulationLocation extends JPanel {
    public Location location;
    private final Action action;

    public SimulationLocation(Position position, boolean isCurrentPosition, Location location, double normalizedCost, Action action) {
        this.location = location;
        this.action = action;

        setLayout(new BorderLayout());

        if (location == Location.START) {
            setName(String.format("Position(%s,%s) - START", position.getX(), position.getY()));
        } else if (location == Location.DESTINATION) {
            setName(String.format("Position(%s,%s) - DESTINATION", position.getX(), position.getY()));
        } else {
            setName(String.format("Position(%s,%s)", position.getX(), position.getY()));
        }

        add(createProbabilityLabel(action, ActionType.UP), BorderLayout.NORTH);
        add(createProbabilityLabel(action, ActionType.DOWN), BorderLayout.SOUTH);
        add(createProbabilityLabel(action, ActionType.LEFT), BorderLayout.WEST);
        add(createProbabilityLabel(action, ActionType.RIGHT), BorderLayout.EAST);

        if (isCurrentPosition) {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/crong.png")));
            Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaled);
            JLabel label = new JLabel(resizedIcon);
            label.setName("Current");
            add(label);
        }
        setPreferredSize(new Dimension(100, 100));
        setBorder(new LineBorder(Color.GRAY, 1));
        int red = (int) (normalizedCost * 255);
        int green = (int) ((1 - normalizedCost) * 255);
        setBackground(new Color(red, green, 0));
    }

    private JLabel createProbabilityLabel(Action action, ActionType type) {
        Probability prob = action.getProbabilities().get(type);
        int value = prob.getEnd() - prob.getStart() + 1;
        JLabel label = new JLabel(String.valueOf(value) + "%", SwingConstants.CENTER);
        label.setName(type.toString());
        return label;
    }
}

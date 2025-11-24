package gui;

import markov.input.Data;
import markov.input.DataLoader;
import markov.cost.CostMapController;
import markov.map.MapReader;
import markov.map.SimulationMap;
import markov.map.SimulationMapController;
import markov.movement.Movement;
import markov.movement.MovementController;
import markov.movement.MovementReader;
import markov.random.OneToHundredRandomGenerator;
import markov.simulation.SimulationController;
import markov.simulation.SimulationTime;

public class Application {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("시뮬레이션 시간만을 인자로 전달해야합니다.");
            return;
        }
        MessageLogger messageLogger = new MessageLogger();
        SimulationPanel simulationPanel = new SimulationPanel(null, new SimulationResultDialog());
        try {
            SimulationTime simulationTime = SimulationTime.Companion.of(args[0]);
            SimulationMap map = new SimulationMapController(messageLogger).readMap(
                    MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP))
            );
            Movement movement = new MovementController(
                    map.getSize(),
                    messageLogger
            ).readMovement(MovementReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.PROBABILITY)));
            simulationPanel = new SimulationPanel(movement, new SimulationResultDialog());
            new CostMapController(simulationPanel).findCostMap(map, movement);
            new SimulationController(simulationPanel).startFrom(
                    map,
                    simulationTime, movement, OneToHundredRandomGenerator.INSTANCE
            );
            simulationPanel.paintSimulation();
        } catch (Exception error) {
            System.out.println("오류가 발생해 프로그램을 종료합니다");
        } finally {
            new MainFrame(messageLogger, simulationPanel);
        }
    }
}

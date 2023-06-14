package gui.GameVisual.Model;

public class Robot {

    public Position position = new Position();

    public volatile double m_robotDirection = 0;
    public final double maxVelocity = 0.1;
    public final double maxAngularVelocity = 0.001;

}

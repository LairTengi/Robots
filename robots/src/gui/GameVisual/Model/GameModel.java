package gui.GameVisual.Model;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel {

    private final gui.GameVisual.Model.Robot robot = new gui.GameVisual.Model.Robot();

    private final Geometry geometry = new Geometry();

    public Robot getRobot() {
        return robot;
    }

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GameModel() {
        Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    public void setTargetPosition(Point p) {
        robot.position.targetX = p.x;
        robot.position.targetY = p.y;
    }


    protected void onModelUpdateEvent() {

        double distance = Geometry.distance(robot.position);

        if (distance < 0.5) {
            return;
        }
        double velocity = robot.maxVelocity;
        double angleToTarget = Geometry.angleTo(robot.position);


        moveRobot(velocity, geometry.handleAngularVelocity(angleToTarget, robot), 10);
    }


    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = Geometry.applyLimits(velocity, 0, robot.maxVelocity);
        angularVelocity = Geometry.applyLimits(angularVelocity, -robot.maxAngularVelocity, robot.maxAngularVelocity);
        double newX = robot.position.posX + velocity / angularVelocity *
                (Math.sin(robot.m_robotDirection + angularVelocity * duration) -
                        Math.sin(robot.m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robot.position.posX + velocity * duration * Math.cos(robot.m_robotDirection);
        }
        double newY = robot.position.posY - velocity / angularVelocity *
                (Math.cos(robot.m_robotDirection + angularVelocity * duration) -
                        Math.cos(robot.m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robot.position.posY + velocity * duration * Math.sin(robot.m_robotDirection);
        }
        robot.position.posX = newX;
        robot.position.posY = newY;
        robot.m_robotDirection = Geometry.asNormalizedRadians(robot.m_robotDirection + angularVelocity * duration);
    }

}
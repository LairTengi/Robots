package gui.GameVisual.Model;


public class Geometry {

    protected static double distance(Position position) {
        double diffX = position.targetX - position.posX;
        double diffY = position.targetY - position.posY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    protected static double angleTo(Position position) {
        double diffX = position.targetX - position.posX;
        double diffY = position.targetY - position.posY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected double handleAngularVelocity(double angleToTarget, final Robot robot) {
        double angularVelocity = 0;
        if (angleToTarget - robot.m_robotDirection > Math.PI) {
            angularVelocity = -robot.maxAngularVelocity;
        }
        if (angleToTarget - robot.m_robotDirection < -Math.PI) {
            angularVelocity = robot.maxAngularVelocity;
        }
        if (angleToTarget - robot.m_robotDirection < Math.PI
                && angleToTarget - robot.m_robotDirection >= 0) {
            angularVelocity = robot.maxAngularVelocity;
        }
        if (angleToTarget - robot.m_robotDirection < 0
                && angleToTarget - robot.m_robotDirection >= -Math.PI) {
            angularVelocity = -robot.maxAngularVelocity;
        }
        if (unreachable(robot)) {
            angularVelocity = 0;
        }
        return angularVelocity;
    }

    protected static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    protected static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    public static int round(double value) {
        return (int) (value + 0.5);
    }


    protected boolean unreachable(final Robot robot) {
        double dx = robot.position.targetX - robot.position.posX;
        double dy = robot.position.targetY - robot.position.posY;

        double new_dx = Math.cos(robot.m_robotDirection) * dx + Math.sin(robot.m_robotDirection) * dy;
        double new_dy = Math.cos(robot.m_robotDirection) * dy - Math.sin(robot.m_robotDirection) * dx;

        double y_center = robot.maxVelocity / robot.maxAngularVelocity;
        double dist1 = (Math.sqrt(Math.pow((new_dx), 2) + Math.pow(new_dy - y_center, 2)));
        double dist2 = (Math.sqrt(Math.pow((new_dx), 2) + Math.pow(new_dy + y_center, 2)));

        return !(dist1 > robot.maxVelocity / robot.maxAngularVelocity)
                || !(dist2 > robot.maxVelocity / robot.maxAngularVelocity);
    }

}
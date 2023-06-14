package gui.GameVisual.View;

import gui.GameVisual.Controller.Controller;
import gui.GameVisual.Model.Geometry;
import gui.GameVisual.Model.GameModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel {
    private final Timer m_timer = initTimer();

    private int m_robotCenterX;
    private int m_robotCenterY;
    private final GameModel model = new GameModel();
    private final Controller controller = new Controller(this);


    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, Geometry.round(model.getRobot().position.posX),
                Geometry.round(model.getRobot().position.posY), model.getRobot().m_robotDirection);
        drawTarget(g2d, model.getRobot().position.targetX, model.getRobot().position.targetY);
    }

    public GameVisualizer() {
        Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        controller.getCurrentWorkPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                model.setTargetPosition(point);
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }


    private void drawRobot(Graphics2D g, int x, int y, double direction) {

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > getHeight()) {
            y = getHeight();
        }
        if (x > getWidth()) {
            x = getWidth();
        }

        int robotCenterX = x;
        m_robotCenterX = x;
        m_robotCenterY = y;
        int robotCenterY = y;
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.BLUE);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    public int getM_robotPositionX() {
        return m_robotCenterX;
    }

    public int getM_robotPositionY() {
        return m_robotCenterY;
    }
}
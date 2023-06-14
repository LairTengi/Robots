package gui.GameVisual.Controller;

import javax.swing.*;

public class Controller {

    private JPanel currWorkPanel;

    public JPanel getCurrentWorkPanel() {
        return currWorkPanel;
    }

    public Controller(final JPanel panel) {
        this.currWorkPanel = panel;
    }
}

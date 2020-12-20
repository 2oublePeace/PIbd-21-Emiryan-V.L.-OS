import javax.swing.*;
import java.awt.*;

public class MonitorPanel extends JPanel {
    Core core;
    public MonitorPanel(Core core) {
        this.core = core;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        core.drawMonitor(g);
    }
}

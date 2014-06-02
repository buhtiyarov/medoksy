package view;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame {
    private static JFrame frame;
    private static JTabbedPane tabs;
    private static Timer memoryUsageTimer;

    public MainFrame() {
        frame = new JFrame(Constants.MAINFRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700, 400));
        tabs = new JTabbedPane();
        frame.getContentPane().add(tabs);
        createMemoryUsageTimer();
    }

    public void addComponent(String title, JComponent component) {
        tabs.add(title, component);
    }

    public void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    private void createMemoryUsageTimer() {
        memoryUsageTimer = new Timer();
        memoryUsageTimer.schedule(new TimerTask() {
            public void run() {
                Runtime rt = Runtime.getRuntime();
                long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
                System.out.println("Memory usage: " + usedMB + "MB");
            }
        }, 0, 10000);
    }
}

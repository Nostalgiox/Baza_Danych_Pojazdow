import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame{
    private JPanel JPanel1;

    public DashboardForm() {
        super("Dashboard Form");
        setTitle("Create an account");
        setContentPane(JPanel1);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DashboardForm extends JFrame{
    private JPanel JPanel1;
    private JButton addButton;
    private JButton button2;
    private JButton button3;
    private JTable CarTable;



    Connection conn;
    final String DB_URL = "jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";

    public DashboardForm() {
        super("Dashboard Form");
        setTitle("Application");
        setContentPane(JPanel1);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddCar addCar = new AddCar(null);
                addCar.setVisible(true);
            }
        });
    }
}



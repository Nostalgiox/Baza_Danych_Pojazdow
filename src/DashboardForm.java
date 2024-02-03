import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DashboardForm extends JFrame{
    private JPanel JPanel1;
    private JButton addButton;
    private JButton usunButton;
    private JButton viewButton;
    private JLabel userLabel;
    private String userLogin;


    Connection conn;
    final String DB_URL = "jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";

    public DashboardForm(String login) {
        super("Dashboard Form");
        this.userLogin = login;
        setTitle("Main Page");
        setContentPane(JPanel1);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userLabel.setText(login);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddCar addCar = new AddCar(null, userLogin);
                addCar.setVisible(true);
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CarDatabase carDatabase = new CarDatabase(userLogin);
                carDatabase.setVisible(true);
            }
        });
        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                deleteCar dc = new deleteCar(userLogin);
                dc.setVisible(true);
            }
        });
    }

}
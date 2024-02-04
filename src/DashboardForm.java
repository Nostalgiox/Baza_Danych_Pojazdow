import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

public class DashboardForm extends JFrame{
    private JPanel JPanel1;
    private JButton addButton;
    private JButton usunButton;
    private JButton viewButton;
    private JLabel userLabel;
    private JButton exportButton;
    private JButton importButton;
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
                AddCar addCar = new AddCar(userLogin);
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
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCSV();
            }
        });
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDatabase();
            }
        });
    }

    //export do pliku CSV metody
    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                exportDataToCSV(fileName);
                JOptionPane.showMessageDialog(this, "Export completed successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during export: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportDataToCSV(String fileName) throws Exception {
        final String DB_URL = "jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM pojazd");

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                writer.print(resultSet.getMetaData().getColumnName(i));
                if (i < columnCount) {
                    writer.print(",");
                }
            }
            writer.println();

            // Write data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.print(resultSet.getString(i));
                    if (i < columnCount) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
        }

        // Close resources
        resultSet.close();
        statement.close();
        connection.close();
    }

    //import bazy danych z pliku SQL
    private void loadDatabase() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select SQL File to Load");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                executeSQLScript(fileName);
                JOptionPane.showMessageDialog(this, "Database loaded successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void executeSQLScript(String fileName) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder script = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }

            Statement statement = connection.createStatement();
            statement.executeUpdate(script.toString());
            statement.close();
        }

        // Close resources
        connection.close();
    }


}
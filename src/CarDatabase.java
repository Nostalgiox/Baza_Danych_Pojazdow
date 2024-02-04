import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CarDatabase extends JFrame {
    private JPanel JPanel1;
    private JPanel TablePanel;
    private String userLogin;

    public CarDatabase(String login) {
        this.userLogin = login;
        JPanel1 = new JPanel(new BorderLayout());

        // Dodaj napis "Lista pojazdów" na samej górze
        JLabel titleLabel = new JLabel("Lista pojazdów", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        JPanel1.add(titleLabel, BorderLayout.NORTH);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC", "root", "");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pojazd");

            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("id"),
                        resultSet.getString("nazwa"),
                        resultSet.getString("rodzaj_paliwa"),
                        resultSet.getString("marka"),
                        resultSet.getString("model"),
                        resultSet.getString("przebieg"),
                        resultSet.getString("rok_produkcji"),
                        resultSet.getString("rodzaj")
                };
                data.add(row);
            }

            String[] columnNames = {"ID", "Nazwa", "Rodzaj Paliwa", "Model", "Przebieg", "Rok Produkcji", "Rodzaj"};
            AbstractTableModel tableModel = new AbstractTableModel() {
                @Override
                public int getRowCount() {
                    return data.size();
                }

                @Override
                public int getColumnCount() {
                    return columnNames.length;
                }

                @Override
                public Object getValueAt(int row, int column) {
                    return data.get(row)[column];
                }

                @Override
                public String getColumnName(int column) {
                    return columnNames[column];
                }
            };

            JTable table = new JTable(tableModel);

            JScrollPane scrollPane = new JScrollPane(table);
            JPanel1.add(scrollPane, BorderLayout.CENTER);

            JButton backButton = new JButton("Wstecz");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    DashboardForm dashboardForm = new DashboardForm(userLogin);
                    dashboardForm.setVisible(true);
                }
            });
            JPanel1.add(backButton, BorderLayout.SOUTH);

            // Ustawienia głównego kontenera
            setContentPane(JPanel1);
            setTitle("Baza pojazdów");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

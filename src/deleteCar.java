import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class deleteCar extends JDialog {

    private JPanel JPanel1;
    private JTextField usunPoNazwieField;
    private JTextField usunPoModeluField;
    private JButton usunButton;
    private JButton backButton;
    private String userLogin;

    public deleteCar(String login) {
        this.userLogin = login;
        setTitle("Usuń");
        setContentPane(JPanel1);
        int width = 430, height = 350;
        setMinimumSize(new Dimension(width, height));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);

        usunButton.addActionListener(e -> {
            removeCar();
        });

        backButton.addActionListener(e -> {
            dispose();
            DashboardForm dashboardForm = new DashboardForm(userLogin);
            dashboardForm.setVisible(true);
        });


    }
    private void removeCar() {
        String marka = usunPoNazwieField.getText();
        String model = usunPoModeluField.getText();

        if(marka.isEmpty() || model.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot remove car from Database without specific model and name",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        deletedCar = deleteCarFromDatabase(marka, model);
        if(deletedCar != null) {
            dispose();
            JOptionPane.showMessageDialog(this, "Car successfully deleted!", "Registered", JOptionPane.INFORMATION_MESSAGE);
            DashboardForm dashboardForm = new DashboardForm(userLogin);
            dashboardForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to delete car.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public DeletedCars deletedCar;
    private DeletedCars deleteCarFromDatabase(String marka, String model) {
        DeletedCars deletedCar = null;


        //sprawdzenie polaczenia do DB
        final String DB_URL = "jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM pojazd WHERE marka LIKE ? AND model LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,marka);
            preparedStatement.setString(2,model);

            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows > 0) {
                deletedCar = new DeletedCars();
                deletedCar.marka = marka;
                deletedCar.model = model;
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deletedCar;
    }

}

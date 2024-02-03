import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class AddCar extends JDialog {
    private JPanel JPanel1;
    private JTextField nameField;
    private JTextField MarkaField;
    private JTextField FuelField;
    private JTextField ModelField;
    private JTextField PrzebiegField;
    private JTextField RokField;
    private JRadioButton carButton;
    private JRadioButton MotorButton;
    private JButton dodajButton;
    private JButton backButton;
    public AddCar(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(JPanel1);
        int width = 760, height = 430;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterCar();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                DashboardForm dashboardForm = new DashboardForm();
                dashboardForm.setVisible(true);
            }
        });
    }
        private void RegisterCar() {
            String nazwa = nameField.getText();
            String marka = MarkaField.getText();
            String rodzaj_paliwa = FuelField.getText();
            String model = ModelField.getText();
            String przebieg = PrzebiegField.getText();
            String rok_produkcji = RokField.getText();
            String rodzaj;

            if(nazwa.isEmpty() || marka.isEmpty() || rodzaj_paliwa.isEmpty() || model.isEmpty() ||
                    przebieg.isEmpty() || rok_produkcji.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all fields",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
//            if(!(carButton.isSelected() && MotorButton.isSelected())) {
//                JOptionPane.showMessageDialog(this, "Please select one of the radio buttons",
//                        "Try again",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            else if((carButton.isSelected() && MotorButton.isSelected())) {
//                JOptionPane.showMessageDialog(this, "Please select one of the radio buttons",
//                        "Try again",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            else if(carButton.isSelected()) rodzaj = "Osobowy";
//            else rodzaj = "Motocykl";
            if(carButton.isSelected()) rodzaj = "Osobowy";
            else if(carButton.isSelected() && MotorButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select one of the radio buttons",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            //
            else rodzaj = "Motocykl";

            addedCar = addCarToDatabase(nazwa, marka, rodzaj_paliwa, model, przebieg, rok_produkcji, rodzaj);
            if(addedCar != null) {
                dispose();
                JOptionPane.showMessageDialog(this, "Car successfully registered!", "Registered", JOptionPane.INFORMATION_MESSAGE);
                DashboardForm dashboardForm = new DashboardForm();
                dashboardForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to register new user.",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        Car addedCar;
        private Car addCarToDatabase(String nazwa, String marka, String rodzaj_paliwa, String model, String przebieg, String rok_produkcji, String rodzaj) {
            Car car = null;

            final String DB_URL = "jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC";
            final String USERNAME = "root";
            final String PASSWORD = "";

            try {
                Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO pojazd (nazwa,marka,rodzaj_paliwa,model,przebieg,rok_produkcji,rodzaj) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,nazwa);
                preparedStatement.setString(2,marka);
                preparedStatement.setString(3,rodzaj_paliwa);
                preparedStatement.setString(4,model);
                preparedStatement.setString(5,przebieg);
                preparedStatement.setString(6,rok_produkcji);
                preparedStatement.setString(7, rodzaj);

                int addedRows = preparedStatement.executeUpdate();
                if(addedRows > 0) {
                    car = new Car();
                    car.nazwa = nazwa;
                    car.marka = marka;
                    car.rodzaj_paliwa = rodzaj_paliwa;
                    car.model = model;
                    car.przebieg = przebieg;
                    car.rok_produkcji = rok_produkcji;
                    car.rodzaj = rodzaj;
                }
                //close connection
                stmt.close();
                conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return car;
        }

}

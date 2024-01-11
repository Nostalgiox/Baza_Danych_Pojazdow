import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog{


    private JPanel JPanel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JButton backButton;
    private JLabel JLogin;
    private JLabel JPassword;
    private JPasswordField passwordField2;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Zarejestruj się");
        setContentPane(JPanel1);
        int width = 450, height = 400;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(parent);
                loginForm.setVisible(true);
            }
        });
    }

    private void RegisterUser () {
        String login = textField1.getText();
        String password = String.valueOf(passwordField1.getPassword());
        String confirmPassword = String.valueOf(passwordField2.getPassword());

        if(login.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(login, password);
        if(user != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public User user;
    private User addUserToDatabase(String login, String password) {
        User user = null;


        //sprawdzenie polaczenia do DB
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (login,password) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);

            //insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0) {
                user = new User();
                user.login = login;
                user.password = password;
            }
            //close connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }



}

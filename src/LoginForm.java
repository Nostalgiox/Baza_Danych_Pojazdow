import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JPanel JPanel1;
    private JTextField JTextField1;
    private JPasswordField JPasswordField1;
    private JButton wyjdzButton;
    private JButton zalogujButton;
    private JButton rejestracjaButton;


    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(JPanel1);
        int width = 450, height = 400;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        zalogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = JTextField1.getText();
                String password = new String(JPasswordField1.getPassword());

                user = getAuthenticateUser(login, password);

                if(user != null) {
                    dispose();
                    DashboardForm dashboardForm = new DashboardForm();
                    dashboardForm.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Login or password are invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        wyjdzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        rejestracjaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationForm registrationForm = new RegistrationForm(parent);
                registrationForm.setVisible(true);
            }
        });
    }

    public User user;
    private User getAuthenticateUser(String login, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/Baza_Pojazdow?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE login=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User();
                user.login = resultSet.getString("login");
                user.password = resultSet.getString("password");
            }
            stmt.close();
            conn.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }


}


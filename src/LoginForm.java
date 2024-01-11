import javax.swing.*;
import java.awt.*;

public class LoginForm extends JDialog{
    private JPanel JPanel1;
    private JTextField JTextField1;
    private JPasswordField JPasswordField1;
    private JButton wyjdzButton;
    private JButton zalogujButton;


    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(JPanel1);
        int width = 450, height = 400;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}


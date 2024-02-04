import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        }
        catch (Exception e) { e.printStackTrace(); }
        LoginForm loginForm = new LoginForm(null);
        loginForm.setVisible(true);

//        DashboardForm df = new DashboardForm("admin");
//        df.setVisible(true);

    }
}
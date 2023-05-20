import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameTextField;

    public LoginFrame() {
        setTitle("ChatApp - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 150));

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create the welcome label
        JLabel welcomeLabel = new JLabel("Welcome to ChatApp");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Create the username input components
        JPanel usernamePanel = new JPanel();
        JLabel inputLabel = new JLabel("Please input username: ");
        usernameTextField = new JTextField(20);
        usernamePanel.add(inputLabel);
        usernamePanel.add(usernameTextField);

        // Create the enter button
        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(CENTER_ALIGNMENT);

        // Add components to the main panel
        mainPanel.add(welcomeLabel);
        mainPanel.add(usernamePanel);
        mainPanel.add(enterButton);

        // ActionListener for the enter button
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                dispose();
                openChatBoxFrame(username);
            }
        });

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        pack();
        setVisible(true);
    }

    private void openChatBoxFrame(String username) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChatBoxFrame chatBoxFrame = new ChatBoxFrame("user name : "+username);

                //chatBoxFrame.addChatMember();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
            }
        });
    }
}

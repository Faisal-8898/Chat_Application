import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatBoxFrame extends JFrame {
    private DefaultListModel<String> chatMembersListModel;
    private JLabel selectedUserLabel;
    private String username;

    public ChatBoxFrame(String username) {
        this.username=username;
        setTitle("chat box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        // Create the chat members panel on the left side
        JPanel chatMembersPanel = new JPanel(new BorderLayout());
        chatMembersPanel.setPreferredSize(new Dimension(150, getHeight()));
        JLabel chatMembersLabel = new JLabel("Active Chat Members");
        chatMembersListModel = new DefaultListModel<>();
        JList<String> chatMembersList = new JList<>(chatMembersListModel);
        chatMembersPanel.add(chatMembersLabel, BorderLayout.NORTH);
        chatMembersPanel.add(new JScrollPane(chatMembersList), BorderLayout.CENTER);

        // Create the chat panel on the right side
        JPanel chatPanel = new JPanel(new BorderLayout());
        JLabel usernameLabel = new JLabel(username);
        JTextArea chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
        chatPanel.add(usernameLabel, BorderLayout.NORTH);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        // Create the input panel at the bottom
        JPanel inputPanel = new JPanel(new BorderLayout());
        selectedUserLabel = new JLabel("Selected user: ");
        JTextField inputTextField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(selectedUserLabel, BorderLayout.WEST);
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the main frame
        getContentPane().add(chatMembersPanel, BorderLayout.WEST);
        getContentPane().add(chatPanel, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // ActionListener for the send button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = inputTextField.getText();
                chatTextArea.append("To "+chatMembersList.getSelectedValue()+": " + message + "\n");
                inputTextField.setText("");
            }
        });

        // ActionListener for the chat members list
        chatMembersList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedUser = chatMembersList.getSelectedValue();
                    selectedUserLabel.setText("Selected user: " + selectedUser);
                }
            }
        });

        pack();
        setVisible(true);
    }

    public void addChatMember(String memberName) {
        chatMembersListModel.addElement(memberName);
    }

    //this will not run from loginframe.
   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ChatBoxFrame chatBoxFrame = new ChatBoxFrame("john");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
                chatBoxFrame.addChatMember("John");
                chatBoxFrame.addChatMember("Alice");
            }
        });
    }*/
}

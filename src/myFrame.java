import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.Random;
import javax.swing.JButton;

    public  class myFrame extends JFrame {

        Panel1 p1=new Panel1();
        Login p2= new Login();
        SignUp p3=new SignUp();

        ChatBox p4=new ChatBox("KM Fahim");
        InboxPanel p5= new InboxPanel();

        public JPanel cards= new JPanel(new CardLayout());



    myFrame(){
        //i1=new ImageIcon("logo.png");


        this.setTitle("ChatApplication");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        cards= new JPanel(new CardLayout());

        cards.add(p2, "login");
        cards.add(p3,"signup");
        cards.add(p1, "welcome");
        add(cards);
        //this.add(p5);
        // this.add(p4);



        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "welcome");
        p1.startProgress();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        cardLayout.show(cards, "login");
        //cardLayout.show(cards, "signup");

    }


    public class Panel1 extends JPanel{
        JProgressBar bar1 = new JProgressBar(0, 100);
        JLabel l1 = new JLabel("WELCOME");
        // ImageIcon i1 = new ImageIcon("newicon.png");



        Panel1() {

            // l1.setIcon(i1);
            l1.setBounds(300, 50, 300, 200);
            l1.setForeground(Color.white);
            l1.setBackground(Color.white);
            //System.out.println("Image width: " + i1.getIconWidth() + ", height: " + i1.getIconHeight());
            //l1.setOpaque(true);


            bar1.setValue(0);
            bar1.setBounds(200, 300, 400, 20);
            //b1.setStringPainted(true);

            bar1.setForeground(new Color(253, 226, 243));
            bar1.setBackground(new Color(42, 47, 79));


            this.setBackground(new Color(42, 47, 79));
            this.setLayout(null);
            this.add(l1);
            this.add(bar1);
            //fill();
        }

        public void startProgress() {
            SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (int i = 0; i <= 100; i++) {
                        TimeUnit.MILLISECONDS.sleep(20);
                        publish(i);
                    }
                    return null;
                }


                protected void process(java.util.List<Integer> chunks) {
                    int value = chunks.get(chunks.size() - 1);
                    bar1.setValue(value);
                }
            };
            worker.execute();

        }

    }

    public class SignUp  extends JPanel implements ActionListener {
        JLabel txt,txt2,txt3,txt4,txt5;
        JButton b3,b4;
        JTextField usr2,usrmail;
        JPasswordField pass2;

        SignUp(){

            txt = new JLabel("Please Sign Up");
            txt.setBounds(280, 50, 300, 50);
            txt.setForeground(Color.red);
            txt.setFont(new Font("",Font.BOLD,30));

            txt4=new JLabel("Enter Email :");
            txt4.setBounds(60, 150, 100, 30);
            txt4.setForeground(Color.red);
            txt4.setFont(new Font("", Font.BOLD, 10));


            txt2=new JLabel("Enter user name :");
            txt2.setBounds(60, 200, 100, 30);
            txt2.setForeground(Color.red);
            txt2.setFont(new Font("", Font.BOLD, 10));

            txt3=new JLabel("Enter password :");
            txt3.setBounds(60, 250, 100, 30);
            txt3.setForeground(Color.red);
            txt3.setFont(new Font("", Font.BOLD, 10));

            usrmail=new JTextField();
            usrmail.setBounds(200, 150, 400, 20);


            usr2=new JTextField();
            usr2.setBounds(200, 200, 400, 20);

            pass2=new JPasswordField();
            pass2.setBounds(200, 250, 400, 20);

            b3=new JButton("Sign up");
            b3.setBounds(380, 300, 80, 30);
            b3.setFocusable(false);
            b3.addActionListener(this);

            b4= new JButton("Cancel");
            b4.setBounds(380, 350, 80, 30);
            b4.setFocusable(false);
            b4.addActionListener(this);

            txt5=new JLabel(" ");
            txt5.setBounds(60, 300, 100, 30);
            txt5.setForeground(Color.red);
            txt5.setFont(new Font("", Font.BOLD, 10));


            this.setBackground(new Color(44, 53, 87));
            this.setLayout(null);
            this.add(txt);
            this.add(txt2);
            this.add(txt3);
            this.add(txt4);
            this.add(usrmail);
            this.add(usr2);
            this.add(pass2);
            this.add(b3);
            this.add(b4);
            this.add(txt5);

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "login");
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String umail = usrmail.getText();
                String unm = usr2.getText();
                String pwd = pass2.getText();
                Random rand = new Random();

                int number = rand.nextInt(1000);
                String username = unm + number;

                try {
                    FileWriter fw = new FileWriter("login.txt", true);
                    fw.write(username + "\t" + pwd + "\n" + umail + "\n");
                    fw.close();
                    txt5.setText("Your username is: " + username);
                    //txt5.setOpaque(true);
                } catch (Exception e) {
                }
            }
        });


    }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
}

    public class Login extends JPanel implements ActionListener {

        JLabel txt,txt2,txt3,l2;
        JButton b1, b2;
        JTextField usr;
        JPasswordField pass;

        Login() {
            txt = new JLabel("Log in");
            txt.setBounds(280, 50, 300, 50);
            txt.setForeground(Color.white);
            txt.setFont(new Font("", Font.BOLD, 30));

            txt2=new JLabel("Enter user name :");
            txt2.setBounds(60, 150, 100, 20);
            txt2.setForeground(Color.red);
            txt2.setFont(new Font("", Font.BOLD, 10));

            txt3=new JLabel("Enter password :");
            txt3.setBounds(60, 200, 100, 20);
            txt3.setForeground(Color.red);
            txt3.setFont(new Font("", Font.BOLD, 10));

            usr = new JTextField();
            usr.setBounds(200, 150, 400, 20);
            //usr.setText("enter user name");

            pass = new JPasswordField();
            pass.setBounds(200, 200, 400, 20);
            //pass.setText("enter password");

            b1 = new JButton("LOGIN");
            b1.setFocusable(false);
            b1.setBounds(350, 250, 80, 30);
            b1.addActionListener(this);

            b2 = new JButton("I don't have an account");
            b2.setFocusable(false);
            b2.setBounds(300, 350, 200, 50);
            b2.addActionListener(this);
            l2 = new JLabel("");
            l2.setBounds(60, 250, 100, 20);
            l2.setForeground(Color.red);


            this.setBackground(new Color(42, 47, 79));
            this.setLayout(null);
            this.add(txt);
            this.add(txt2);
            this.add(txt3);
            this.add(usr);
            this.add(pass);
            this.add(b1);
            this.add(b2);
            this.add(l2);

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "signup");
            }});


        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                boolean matched = false;
                String uname = usr.getText().toString();
                String pwd = pass.getText().toString();
                try {
                    FileReader fr = new FileReader("login.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.equals(uname + "\t" + pwd)) {
                            matched = true;
                            break;
                        }
                    }
                    fr.close();
                } catch (Exception e) {};
                if (matched) {
                    l2.setText("Welcome");

                } else {

                }

            }
        });
    }


        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

    }


    public class ChatBox extends JPanel {
        private JLabel contactNameLabel;
        private JButton backButton;
        private JTextArea chatTextArea;
        private JTextField messageTextField;
        private JButton sendButton;

        public ChatBox(String contactName) {
            // Set the panel layout
            setLayout(new BorderLayout());

            // Create panel for contact name and back button
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(new Color(42, 47, 79));
            contactNameLabel = new JLabel(contactName, SwingConstants.CENTER);
            contactNameLabel.setForeground(Color.RED);
            topPanel.add(contactNameLabel, BorderLayout.CENTER);
            backButton = new JButton("Back");
            topPanel.add(backButton, BorderLayout.WEST);

            // Add top panel to the top of the main panel
            add(topPanel, BorderLayout.NORTH);

            // Add chat text area with scroll pane to the center of the panel
            chatTextArea = new JTextArea();
            chatTextArea.setEditable(false);
            chatTextArea.setBackground(Color.GRAY);
            chatTextArea.setForeground(Color.WHITE);
            JScrollPane scrollPane = new JScrollPane(chatTextArea);
            add(scrollPane, BorderLayout.CENTER);

            // Add message text field and send button to the bottom of the panel
            JPanel bottomPanel = new JPanel(new BorderLayout());
            messageTextField = new JTextField();
            bottomPanel.add(messageTextField, BorderLayout.CENTER);
            sendButton = new JButton("Send");
            bottomPanel.add(sendButton, BorderLayout.EAST);
            add(bottomPanel, BorderLayout.SOUTH);

            // Add action listener to send button
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = messageTextField.getText();
                    if (!message.isEmpty()) {
                        chatTextArea.append("Me: " + message + "\n");
                        messageTextField.setText("");
                    }
                }
            });
        }


    }


    public class InboxPanel extends JPanel {
        private JLabel userNameLabel;
        private JTextField searchField;
        private JPanel contactListPanel;

        public InboxPanel() {
            setLayout(new BorderLayout());

            // User Info
            JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel userAvatarLabel = new JLabel("[User Avatar]");
            userNameLabel = new JLabel("[User Name]");
            userInfoPanel.add(userAvatarLabel);
            userInfoPanel.add(userNameLabel);
            add(userInfoPanel, BorderLayout.NORTH);

            // Search Bar
            JPanel searchBarPanel = new JPanel(new BorderLayout());
            searchField = new JTextField(20);
            searchBarPanel.add(searchField, BorderLayout.CENTER);
            add(searchBarPanel, BorderLayout.CENTER);

            // Contact List
            JScrollPane contactListScrollPane = new JScrollPane();
            contactListPanel = new JPanel();
            contactListPanel.setLayout(new BoxLayout(contactListPanel, BoxLayout.Y_AXIS));
            contactListScrollPane.setViewportView(contactListPanel);
            add(contactListScrollPane, BorderLayout.SOUTH);
        }

        public void addContact(String contactName, String lastMessagePreview, String lastMessageTime) {
            JPanel contactItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel contactAvatarLabel = new JLabel("[Contact Avatar]");
            JLabel contactNameLabel = new JLabel(contactName);
            JLabel contactPreviewLabel = new JLabel(lastMessagePreview);
            JLabel contactTimeLabel = new JLabel(lastMessageTime);
            contactItemPanel.add(contactAvatarLabel);
            contactItemPanel.add(contactNameLabel);
            contactItemPanel.add(contactPreviewLabel);
            contactItemPanel.add(contactTimeLabel);
            contactListPanel.add(contactItemPanel);
            revalidate();
            repaint();
        }
    }
}

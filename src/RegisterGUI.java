import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGUI extends JFrame {
    private JFrame registerFrame;
    private JLabel appName, userRegister, usernameLabel, passwordLabel, close, bottomPanel, oldUser, login;
    private JTextField username, password;
    private JButton registerBtn;
    private int posX = 0, posY = 0;
    private ConnectionClass connectionClass;

    public RegisterGUI(){
        registerFrame = new JFrame();

        appName = new JLabel("Stocker");
        appName.setBounds(400, 20, 300, 50);
        appName.setForeground(Color.white);
        appName.setFont(appName.getFont().deriveFont(50.0f));

        userRegister = new JLabel("Register");
        userRegister.setBounds(440, 130, 200, 30);
        userRegister.setForeground(Color.white);
        userRegister.setFont(userRegister.getFont().deriveFont(25.04f));

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(400, 170, 200, 30);
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(15.04f));

        username = new JTextField();
        username.setBounds(400, 200, 200, 30);
        username.setBackground(Color.decode("#99aab5"));

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(400, 240, 200, 30);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(usernameLabel.getFont().deriveFont(15.04f));

        password = new JPasswordField();
        password.setBounds(400, 270, 200, 30);
        password.setBackground(Color.decode("#99aab5"));

        registerBtn = new JButton("Register");
        registerBtn.setBounds(450, 310, 100, 50);
        registerBtn.setBackground(Color.decode("#7289da"));
        registerBtn.setForeground(Color.white);
        registerBtn.setFont(registerBtn.getFont().deriveFont(15.04f));
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userString = username.getText().toString().toLowerCase().trim();
                String passString = password.getText().toString();
                if(!userString.isEmpty() && !passString.isEmpty()){
                    connectionClass = new ConnectionClass();
                    try {
                        Boolean result = connectionClass.registerUser(userString, passString, "user_details");
                        if(result){
                            JOptionPane.showMessageDialog(registerFrame, "Welcome to Stocker!", "Registration Successful", JOptionPane.INFORMATION_MESSAGE);

                            new StartGUI();
                            registerFrame.dispose();
                        }else{
                            JOptionPane.showMessageDialog(registerFrame, "Username already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(registerFrame, "Username/Password can't be empty.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        oldUser = new JLabel("Already have an Account?");
        oldUser.setBounds(400, 350, 190, 50);
        oldUser.setForeground(Color.white);
        oldUser.setFont(oldUser.getFont().deriveFont(15.04f));

        login = new JLabel("Login");
        login.setBounds(585, 360, 80, 30);
        login.setForeground(Color.decode("#7289da"));
        login.setFont(login.getFont().deriveFont(15.04f));
        login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new StartGUI();
                registerFrame.dispose();
            }
        });

        close = new JLabel("X", SwingConstants.CENTER);
        close.setBounds(970, 0, 30, 30);
        close.setForeground(Color.white);
        close.setFont(close.getFont().deriveFont(30.04f));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                close.setOpaque(true);
                close.setBackground(Color.decode("#ff0000"));
            }
        });
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                close.setOpaque(true);
                close.setBackground(null);
            }
        });
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });

        bottomPanel = new JLabel("Copyright © 2020", SwingConstants.CENTER);
        bottomPanel.setBounds(0, 550, 1000, 50);
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(Color.decode("#23272a"));
        bottomPanel.setForeground(Color.white);
        bottomPanel.setFont(bottomPanel.getFont().deriveFont(15.04f));

//        Add components.
        registerFrame.add(appName);
        registerFrame.add(close);

        registerFrame.add(userRegister);
        registerFrame.add(usernameLabel);
        registerFrame.add(username);
        registerFrame.add(passwordLabel);
        registerFrame.add(password);
        registerFrame.add(registerBtn);
        registerFrame.add(oldUser);
        registerFrame.add(login);

        registerFrame.add(bottomPanel);

        //      Set size of JFrame.
        registerFrame.setSize(1000, 600);
        registerFrame.setLayout(null);

        //        Get JFrame in centre.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        registerFrame.setLocation(dim.width/2-registerFrame.getSize().width/2, dim.height/2-registerFrame.getSize().height/2);

//        Set JFrame as Visible.
        registerFrame.getContentPane().setBackground(Color.decode("#2c2f33"));
        registerFrame.setUndecorated(true);
        registerFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                posX=e.getX();
                posY=e.getY();
            }
        });
        registerFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                registerFrame.setLocation (e.getXOnScreen()-posX,e.getYOnScreen()-posY);
            }
        });
        registerFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterGUI();
    }
}

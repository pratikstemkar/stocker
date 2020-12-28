import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartGUI extends JFrame {
    private JFrame startFrame;
    private JLabel appName, userLogin, usernameLabel, passwordLabel, adminUsernameLabel, adminPasswordLabel, adminLogin, close, bottomPanel, newUser, register;
    private JTextField username, password, adminUsername, adminPassword;
    private JButton loginBtn, adminLoginBtn;
    private int posX=0, posY = 0;
    private ConnectionClass connectionClass;

    public StartGUI(){
        startFrame = new JFrame();

        appName = new JLabel("Stocker");
        appName.setBounds(400, 20, 300, 50);
        appName.setForeground(Color.white);
        appName.setFont(appName.getFont().deriveFont(50.0f));

        userLogin = new JLabel("User Login");
        userLogin.setBounds(200, 130, 200, 30);
        userLogin.setForeground(Color.white);
        userLogin.setFont(userLogin.getFont().deriveFont(25.04f));

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(200, 170, 200, 30);
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(15.04f));

        username = new JTextField();
        username.setBounds(200, 200, 200, 30);
        username.setBackground(Color.decode("#99aab5"));
        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    String userString = username.getText().toString().toLowerCase().trim();
                    String passString = password.getText().toString();
                    if(!userString.isEmpty() && !passString.isEmpty()){
                        connectionClass = new ConnectionClass();
                        try {
                            Boolean result = connectionClass.checkLogin(userString, passString, "user_details");
                            if(result){
                                JOptionPane.showMessageDialog(startFrame, "Welcome to Stocker!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                                connectionClass.setActive(userString, "user_details");
                                new UserGUI();
                                startFrame.dispose();
                            }else{
                                JOptionPane.showMessageDialog(startFrame, "Please check your username or password and try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(startFrame, "Username/Password can't be empty.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(200, 240, 200, 30);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(usernameLabel.getFont().deriveFont(15.04f));

        password = new JPasswordField();
        password.setBounds(200, 270, 200, 30);
        password.setBackground(Color.decode("#99aab5"));
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    String userString = username.getText().toString().toLowerCase().trim();
                    String passString = password.getText().toString();
                    if(!userString.isEmpty() && !passString.isEmpty()){
                        connectionClass = new ConnectionClass();
                        try {
                            Boolean result = connectionClass.checkLogin(userString, passString, "user_details");
                            if(result){
                                JOptionPane.showMessageDialog(startFrame, "Welcome to Stocker!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                                connectionClass.setActive(userString, "user_details");
                                new UserGUI();
                                startFrame.dispose();
                            }else{
                                JOptionPane.showMessageDialog(startFrame, "Please check your username or password and try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(startFrame, "Username/Password can't be empty.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        loginBtn = new JButton("Login");
        loginBtn.setBounds(250, 310, 100, 50);
        loginBtn.setBackground(Color.decode("#7289da"));
        loginBtn.setForeground(Color.white);
        loginBtn.setFont(loginBtn.getFont().deriveFont(15.04f));
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userString = username.getText().toString().toLowerCase().trim();
                String passString = password.getText().toString();
                if(!userString.isEmpty() && !passString.isEmpty()){
                    connectionClass = new ConnectionClass();
                    try {
                        Boolean result = connectionClass.checkLogin(userString, passString, "user_details");
                        if(result){
                            JOptionPane.showMessageDialog(startFrame, "Welcome to Stocker!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                            connectionClass.setActive(userString, "user_details");
                            new UserGUI();
                            startFrame.dispose();
                        }else{
                            JOptionPane.showMessageDialog(startFrame, "Please check your username or password and try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(startFrame, "Username/Password can't be empty.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        newUser = new JLabel("New here?");
        newUser.setBounds(200, 350, 80, 50);
        newUser.setForeground(Color.white);
        newUser.setFont(newUser.getFont().deriveFont(15.04f));

        register = new JLabel("Register");
        register.setBounds(280, 360, 80, 30);
        register.setForeground(Color.decode("#7289da"));
        register.setFont(newUser.getFont().deriveFont(15.04f));
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new RegisterGUI();
                startFrame.dispose();
            }
        });

        adminLogin = new JLabel("Admin Login");
        adminLogin.setBounds(600, 130, 200, 30);
        adminLogin.setForeground(Color.white);
        adminLogin.setFont(adminLogin.getFont().deriveFont(25.04f));

        adminUsernameLabel = new JLabel("Username");
        adminUsernameLabel.setBounds(600, 170, 200, 30);
        adminUsernameLabel.setForeground(Color.white);
        adminUsernameLabel.setFont(adminUsernameLabel.getFont().deriveFont(15.04f));

        adminUsername = new JTextField();
        adminUsername.setBounds(600, 200, 200, 30);
        adminUsername.setBackground(Color.decode("#99aab5"));

        adminPasswordLabel = new JLabel("Password");
        adminPasswordLabel.setBounds(600, 240, 200, 30);
        adminPasswordLabel.setForeground(Color.white);
        adminPasswordLabel.setFont(adminPasswordLabel.getFont().deriveFont(15.04f));

        adminPassword = new JPasswordField();
        adminPassword.setBounds(600, 270, 200, 30);
        adminPassword.setBackground(Color.decode("#99aab5"));

        adminLoginBtn = new JButton("Login");
        adminLoginBtn.setBounds(650, 310, 100, 50);
        adminLoginBtn.setBackground(Color.decode("#7289da"));
        adminLoginBtn.setForeground(Color.white);
        adminLoginBtn.setFont(adminLoginBtn.getFont().deriveFont(15.04f));
        adminLoginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        adminLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userString = adminUsername.getText().toString().toLowerCase().trim();
                String passString = adminPassword.getText().toString();
                if(!userString.isEmpty() && !passString.isEmpty()){
                    connectionClass = new ConnectionClass();
                    try {
                        Boolean result = connectionClass.checkLogin(userString, passString, "admin_details");
                        if(result){
                            JOptionPane.showMessageDialog(startFrame, "Welcome to Stocker Admin Panel!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                            connectionClass.setActive(userString, "admin_details");
                            new AdminGUI();
                            startFrame.dispose();
                        }else{
                            JOptionPane.showMessageDialog(startFrame, "Please check your username or password and try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(startFrame, "Username/Password can't be empty.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
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
        startFrame.add(appName);
        startFrame.add(close);

        startFrame.add(userLogin);
        startFrame.add(usernameLabel);
        startFrame.add(username);
        startFrame.add(passwordLabel);
        startFrame.add(password);
        startFrame.add(loginBtn);
        startFrame.add(newUser);
        startFrame.add(register);

        startFrame.add(adminLogin);
        startFrame.add(adminUsernameLabel);
        startFrame.add(adminUsername);
        startFrame.add(adminPassword);
        startFrame.add(adminPasswordLabel);
        startFrame.add(adminLoginBtn);

        startFrame.add(bottomPanel);

        //      Set size of JFrame.
        startFrame.setSize(1000, 600);
        startFrame.setLayout(null);

        //        Get JFrame in centre.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        startFrame.setLocation(dim.width/2-startFrame.getSize().width/2, dim.height/2-startFrame.getSize().height/2);

//        Set JFrame as Visible.
        startFrame.getContentPane().setBackground(Color.decode("#2c2f33"));
        startFrame.setUndecorated(true);
        startFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                posX=e.getX();
                posY=e.getY();
            }
        });
        startFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                startFrame.setLocation (e.getXOnScreen()-posX,e.getYOnScreen()-posY);
            }
        });
        startFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new StartGUI();
    }
}

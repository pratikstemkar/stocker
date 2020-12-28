import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class UserGUI extends JFrame {
    private JFrame userFrame;
    private JLabel appName, close, bottomPanel, pName, uName, invest, investR, current, currentR, profit;
    private JTable portTable;
    private JScrollPane scroller;
    private JButton buyBtn, sellBtn, refreshBtn, logoutBtn;
    private int posX = 0, posY = 0;
    private ConnectionClass connectionClass;

    public UserGUI(){
        userFrame = new JFrame();

        connectionClass = new ConnectionClass();

        appName = new JLabel("Stocker");
        appName.setBounds(10, 10, 300, 30);
        appName.setForeground(Color.white);
        appName.setFont(appName.getFont().deriveFont(30.0f));

        pName = new JLabel("User Panel");
        pName.setBounds(130, 18, 150, 20);
        pName.setForeground(Color.decode("#7289da"));
        pName.setFont(pName.getFont().deriveFont(20.0f));

        uName = new JLabel("sdf");
        uName.setBounds(10, 38, 150, 20);
        uName.setForeground(Color.decode("#ffffff"));
        uName.setFont(uName.getFont().deriveFont(12.0f));
        try{
            uName.setText(connectionClass.getActive("user_details"));
        }catch(Exception e){
            e.printStackTrace();
        }

        invest = new JLabel("Total Investment");
        invest.setBounds(400, 10, 180, 40);
        invest.setFont(invest.getFont().deriveFont(20.0f));
        invest.setForeground(Color.decode("#7289da"));

        investR = new JLabel();
        investR.setBounds(420, 40, 180, 40);
        investR.setFont(invest.getFont().deriveFont(25.0f));
        investR.setForeground(Color.white);
        try{
            investR.setText("" + connectionClass.getInvestment(connectionClass.getActive("user_details")));
        }catch(Exception e){
            e.printStackTrace();
        }

        current = new JLabel("Current Value");
        current.setBounds(700, 10, 180, 40);
        current.setFont(current.getFont().deriveFont(20.0f));
        current.setForeground(Color.decode("#7289da"));

        currentR = new JLabel();
        currentR.setBounds(720, 40, 180, 40);
        currentR.setFont(currentR.getFont().deriveFont(25.0f));
        currentR.setForeground(Color.white);
        try{
            currentR.setText("" + connectionClass.getCurrentValue(connectionClass.getActive("user_details")));
        }catch(Exception e){
            e.printStackTrace();
        }

        profit = new JLabel();
        profit.setBounds(580, 80, 180, 40);
        profit.setFont(profit.getFont().deriveFont(25.0f));
        profit.setForeground(Color.white);
        try{
            if(connectionClass.getProfit(connectionClass.getActive("user_details")) >= 0){
                profit.setForeground(Color.green);
                profit.setText("" + Math.abs(connectionClass.getProfit(connectionClass.getActive("user_details"))));
            }else{
                profit.setForeground(Color.red);
                profit.setText("" + Math.abs(connectionClass.getProfit(connectionClass.getActive("user_details"))));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        //        Get the PORTFOLIO DATA
        try{
            String url = "jdbc:mysql://localhost:3306/stocker?autoReconnect=true&useSSL=false";
            String username = "root";
            String password = "root";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            String query = "SELECT sid, nos, bprice from portfolio where username = '"+ connectionClass.getActive("user_details") +"';";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            portTable = new JTable(buildTableModel(rs)){
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };

            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        portTable.setFont(portTable.getFont().deriveFont(15.04f));
        portTable.setBackground(Color.decode("#2c2f33"));
        portTable.setForeground(Color.white);
        portTable.setSelectionBackground(Color.decode("#7289da"));
        portTable.setSelectionForeground(Color.white);
        portTable.setRowHeight(20);
        portTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroller = new JScrollPane(portTable);
        scroller.getViewport().setBackground(Color.decode("#2c2f33"));
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setBounds(250, 120, 500, 300);

        //      CRUD Operations
        buyBtn = new JButton("Buy Stock");
        buyBtn.setBackground(Color.decode("#7289da"));
        buyBtn.setForeground(Color.white);
        buyBtn.setFont(buyBtn.getFont().deriveFont(15.04f));
        buyBtn.setBounds(50, 120, 140, 50);
        buyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuyGUI();
                userFrame.dispose();
            }
        });

        sellBtn = new JButton("Sell Stock");
        sellBtn.setBackground(Color.decode("#ea3c53"));
        sellBtn.setForeground(Color.white);
        sellBtn.setFont(sellBtn.getFont().deriveFont(15.04f));
        sellBtn.setBounds(50, 180, 140, 50);
        sellBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionClass = new ConnectionClass();
                int row = portTable.getSelectedRow();
                if(row == -1)
                    JOptionPane.showMessageDialog(userFrame, "Select a stock to sell.", "Deal Failed!",JOptionPane.ERROR_MESSAGE);
                else{
                    try{
                        String sid = (portTable.getModel().getValueAt(row, 0).toString());
                        if(connectionClass.sellStock(sid, connectionClass.getActive("user_details")))
                            JOptionPane.showMessageDialog(userFrame, "Selected stock sold. Refresh to see results.", "Deal Successful!",JOptionPane.INFORMATION_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(userFrame, "Error in stock deal.", "Deal Failed!",JOptionPane.ERROR_MESSAGE);
                    }catch(Exception e1){
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(userFrame, "Error in stock deal.", "Deal Failed!",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(Color.decode("#ffffff"));
        refreshBtn.setForeground(Color.decode("#7289da"));
        refreshBtn.setFont(refreshBtn.getFont().deriveFont(15.04f));
        refreshBtn.setBounds(50, 240, 140, 50);
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //        Get the STOCK DATA
                new UserGUI();
                userFrame.dispose();
            }
        });

        logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.decode("#23272a"));
        logoutBtn.setForeground(Color.decode("#ffffff"));
        logoutBtn.setFont(logoutBtn.getFont().deriveFont(15.04f));
        logoutBtn.setBounds(50, 300, 140, 50);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionClass = new ConnectionClass();
                try{
                    connectionClass.removeActive("user_details");
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                new StartGUI();
                userFrame.dispose();
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
                close.setBackground(Color.decode("#ea3c53"));
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
                connectionClass = new ConnectionClass();
                try{
                    connectionClass.removeActive("user_details");
                }catch (Exception e1){
                    e1.printStackTrace();
                }
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
        userFrame.add(appName);
        userFrame.add(close);
        userFrame.add(pName);
        userFrame.add(uName);
        userFrame.add(invest);
        userFrame.add(investR);
        userFrame.add(current);
        userFrame.add(currentR);
        userFrame.add(profit);

        userFrame.add(scroller);

        userFrame.add(buyBtn);
        userFrame.add(sellBtn);
        userFrame.add(refreshBtn);
        userFrame.add(logoutBtn);

        userFrame.add(bottomPanel);

        //      Set size of JFrame.
        userFrame.setSize(1000, 600);
        userFrame.setLayout(null);

        //        Get JFrame in centre.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        userFrame.setLocation(dim.width/2-userFrame.getSize().width/2, dim.height/2-userFrame.getSize().height/2);

//        Set JFrame as Visible.
        userFrame.getContentPane().setBackground(Color.decode("#2c2f33"));
        userFrame.setUndecorated(true);
        userFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                posX=e.getX();
                posY=e.getY();
            }
        });
        userFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                userFrame.setLocation (e.getXOnScreen()-posX,e.getYOnScreen()-posY);
            }
        });
        userFrame.setVisible(true);
    }

    //   RESULT SET TO TABLE METHOD
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public static void main(String[] args) {
        new UserGUI();
    }
}


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class BuyGUI extends JFrame {
    private JFrame buyFrame;
    private JLabel appName, close, bottomPanel, pName, uName;
    private JTable stockTable;
    private JScrollPane scroller;
    private JButton buyBtn, sellBtn, refreshBtn, logoutBtn;
    private int posX = 0, posY = 0;
    private ConnectionClass connectionClass;

    public BuyGUI(){
        buyFrame = new JFrame();

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

//        Get the STOCK DATA
        try{
            String url = "jdbc:mysql://localhost:3306/stocker?autoReconnect=true&useSSL=false";
            String username = "root";
            String password = "root";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            String query = "SELECT * from stock_details;";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            stockTable = new JTable(buildTableModel(rs)){
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };

            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        stockTable.setFont(stockTable.getFont().deriveFont(15.04f));
        stockTable.setBackground(Color.decode("#2c2f33"));
        stockTable.setForeground(Color.white);
        stockTable.setSelectionBackground(Color.decode("#7289da"));
        stockTable.setSelectionForeground(Color.white);
        stockTable.setRowHeight(20);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroller = new JScrollPane(stockTable);
        scroller.getViewport().setBackground(Color.decode("#2c2f33"));
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setBounds(250, 100, 500, 400);

//      CRUD Operations
        buyBtn = new JButton("Buy Stock");
        buyBtn.setBackground(Color.decode("#7289da"));
        buyBtn.setForeground(Color.white);
        buyBtn.setFont(buyBtn.getFont().deriveFont(15.04f));
        buyBtn.setBounds(50, 120, 140, 50);
        buyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionClass = new ConnectionClass();
                int row = stockTable.getSelectedRow();
                if(row == -1){
                    JOptionPane.showMessageDialog(buyFrame, "Select a stock to buy.", "Deal Failed!",JOptionPane.ERROR_MESSAGE);
                }else{
                    String sid = (stockTable.getModel().getValueAt(row, 0).toString());
                    try{
                        JLabel stockIdLabel = new JLabel("Stock ID");
                        JTextField stockId = new JTextField();
                        JLabel stockNameLabel = new JLabel("Stock Name");
                        JTextField stockName = new JTextField();
                        JLabel stockPriceLabel = new JLabel("Stock Price");
                        JTextField stockPrice = new JTextField();
                        JLabel quantLabel = new JLabel("Quantity");
                        JTextField quant = new JTextField();

                        stockId.setText(sid);
                        stockId.setEditable(false);
                        stockName.setText(connectionClass.getSname(sid));
                        stockName.setEditable(false);
                        stockPrice.setText(connectionClass.getCprice(sid) + "");
                        stockPrice.setEditable(false);

                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(stockIdLabel);
                        panel.add(stockId);
                        panel.add(stockNameLabel);
                        panel.add(stockName);
                        panel.add(stockPriceLabel);
                        panel.add(stockPrice);
                        panel.add(quantLabel);
                        panel.add(quant);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Update Stock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if(result == JOptionPane.OK_OPTION){
                            String ssid = stockId.getText().toString();
                            String sname = stockName.getText().toString();
                            double cprice = Double.parseDouble(stockPrice.getText());
                            int nos = Integer.parseInt(quant.getText());
                            if(connectionClass.buyStock(connectionClass.getActive("user_details"), ssid, cprice, nos))
                                JOptionPane.showMessageDialog(buyFrame, "Stocks for " + sname + " bought successfully.", "Deal Successful!",JOptionPane.INFORMATION_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(buyFrame, "Stock dealing failed.", "Failed!",JOptionPane.ERROR_MESSAGE);
                        }else{
                            System.out.println("Deal Aborted!");
                        }
                    }catch(Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(Color.decode("#ffffff"));
        refreshBtn.setForeground(Color.decode("#7289da"));
        refreshBtn.setFont(refreshBtn.getFont().deriveFont(15.04f));
        refreshBtn.setBounds(50, 180, 140, 50);
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //        Get the STOCK DATA
                new BuyGUI();
                buyFrame.dispose();
            }
        });

        logoutBtn = new JButton("Go Back");
        logoutBtn.setBackground(Color.decode("#23272a"));
        logoutBtn.setForeground(Color.decode("#ffffff"));
        logoutBtn.setFont(logoutBtn.getFont().deriveFont(15.04f));
        logoutBtn.setBounds(50, 240, 140, 50);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserGUI();
                buyFrame.dispose();
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
        buyFrame.add(appName);
        buyFrame.add(close);
        buyFrame.add(pName);
        buyFrame.add(uName);

        buyFrame.add(scroller);

        buyFrame.add(buyBtn);
        buyFrame.add(refreshBtn);
        buyFrame.add(logoutBtn);

        buyFrame.add(bottomPanel);

        //      Set size of JFrame.
        buyFrame.setSize(1000, 600);
        buyFrame.setLayout(null);

        //        Get JFrame in centre.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        buyFrame.setLocation(dim.width/2-buyFrame.getSize().width/2, dim.height/2-buyFrame.getSize().height/2);

//        Set JFrame as Visible.
        buyFrame.getContentPane().setBackground(Color.decode("#2c2f33"));
        buyFrame.setUndecorated(true);
        buyFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                posX=e.getX();
                posY=e.getY();
            }
        });
        buyFrame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                buyFrame.setLocation (e.getXOnScreen()-posX,e.getYOnScreen()-posY);
            }
        });
        buyFrame.setVisible(true);
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
        new BuyGUI();
    }
}

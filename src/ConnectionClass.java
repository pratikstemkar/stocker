import java.sql.*;

public class ConnectionClass {

    public Connection createConnection() throws Exception{
        String url = "jdbc:mysql://localhost:3306/stocker?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "root";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

//        System.out.println("\n Database connection established");

        return con;
    }

    public Boolean checkLogin(String username, String password, String table_name) throws Exception{
        Connection con = createConnection();
        Boolean result = false;

        String query = "Select * from "+ table_name + " where username = '" + username + "' AND password = '" + password + "' ;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        if(rs.next()){
            result = true;
        } else{
            result = false;
        }

        st.close();
        con.close();

        return result;
    }

    public Boolean registerUser(String username, String password, String table_name){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "INSERT INTO " + table_name + "(username, password) values ('"+username+"', '"+password+"');";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public Boolean deleteUser(String username){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "DELETE from user_details where username = '" + username + "';";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public void setActive(String username, String table_name) throws Exception{
        Connection con = createConnection();

        String query = "UPDATE " + table_name + " SET active = 1 where username = '" + username + "';";
        Statement st = con.createStatement();
        st.executeUpdate(query);

        st.close();
        con.close();
    }

    public void removeActive(String table_name) throws Exception{
        Connection con = createConnection();

        String query = "UPDATE " + table_name + " SET active = 0;";
        Statement st = con.createStatement();
        st.executeUpdate(query);

        st.close();
        con.close();
    }

    public String getActive(String table_name) throws Exception{
        Connection con = createConnection();

        String result = "";
        String query = "select username from " + table_name + " where active = 1;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        if(rs.next())
            result = rs.getString("username");
        else
            result = "not_found";
        st.close();
        con.close();

        System.out.println(result);

        return result;
    }

    public Boolean addStock(String sid, String sname, double cprice){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "INSERT INTO stock_details (sid, sname, cprice) values ('"+sid+"', '"+sname+"', '"+cprice+"');";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public Boolean deleteStock(String sid){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "DELETE from stock_details where sid = '" + sid + "';";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public Boolean sellStock(String sid, String username){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "DELETE from portfolio where sid = '" + sid + "' and username = '"+username+"';";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public Boolean updateStock(String sid, String sname, double cprice){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "UPDATE stock_details set sid = '" + sid + "', sname = '" + sname + "', cprice = '"+ cprice +"' where sid = '" + sid + "';";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public Boolean buyStock(String username, String sid, double cprice, int nos){
        try{
            Connection con = createConnection();
            Boolean result = false;

            String query = "INSERT into portfolio values ('"+username+"', '"+sid+"', '"+nos+"', '"+cprice+"');";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            result = (rs > 0);

            st.close();
            con.close();

            return result;
        } catch(Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public String getSname(String sid) throws Exception{
        Connection con = createConnection();
        Statement st = con.createStatement();
        String query = "SELECT sname from stock_details where sid = '"+ sid +"'";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        String sname = rs.getString("sname");
        return sname;
    }

    public double getCprice(String sid) throws Exception{
        Connection con = createConnection();
        Statement st = con.createStatement();
        String query = "SELECT cprice from stock_details where sid = '"+ sid +"'";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        double cprice = rs.getDouble("cprice");
        return cprice;
    }

    public double getInvestment(String username) throws Exception{
        Connection con = createConnection();
        Statement st = con.createStatement();
        String query = "SELECT nos, bprice from portfolio where username = '"+username+"';";
        double sum = 0;
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            int nos = rs.getInt("nos");
            double bprice = rs.getDouble("bprice");
            sum += nos*bprice;
        }

        st.close();
        con.close();

        System.out.println(sum);
        return sum;
    }

    public double getCurrentValue(String username) throws Exception{
        Connection con = createConnection();
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        String query = "SELECT sid, nos, bprice from portfolio where username = '"+username+"';";

        double sum = 0;
        double sum1 = 0;
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            int nos = rs.getInt("nos");
            double bprice = rs.getDouble("bprice");
            String sid = rs.getString("sid");
            String query1 = "SELECT cprice from stock_details where sid = '"+sid+"';";
            ResultSet rs1 = st1.executeQuery(query1);
            rs1.next();
            double cprice = rs1.getDouble("cprice");
            sum += nos*bprice;
            sum1 += nos*cprice;
        }

        st.close();
        con.close();

        System.out.println(sum1);
        return sum1;
    }

    public double getProfit(String username) throws Exception{
        double profit = 0;
        profit = getCurrentValue(username) - getInvestment(username);
        System.out.println(profit);
        return profit;
    }
}

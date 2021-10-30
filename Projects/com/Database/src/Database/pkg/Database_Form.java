package Database.pkg;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;

public class Database_Form extends JDialog {
    private JPanel panel1;
    private JTable tblDatabase;


    public Database_Form(JFrame owner) {
        super(owner);
        tblDatabase.getTableHeader().setReorderingAllowed(false);
        panel1.setPreferredSize(new Dimension(375, 575));
        setTitle("Database/pkg");
        setModalityType(ModalityType.DOCUMENT_MODAL);
        setContentPane(panel1);
        createTable();

    }


    public void createTable() {


        String[] column = new String[]{"X", "Y"};

        DefaultTableModel model = new DefaultTableModel(null, column);
        tblDatabase.setModel(model);


        String url = "jdbc:sqlserver://DMC\\MSSQLSERVER:1433;database=master";
        String user = "Diego";
        String pass = "mrman1091";
        try {
            DriverManager.registerDriver(new SQLServerDriver());

            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();
            String sql = "select * from [Database]";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String x = String.valueOf(rs.getString("X"));
                String y = rs.getString("Y");
                String[] tbData = {x, y};
                model.addRow(tbData);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        TableColumnModel columns = tblDatabase.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        columns.getColumn(0).setMinWidth(75);
        columns.getColumn(1).setMinWidth(75);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(1).setCellRenderer(centerRenderer);


    }


}

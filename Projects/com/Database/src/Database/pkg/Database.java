package Database.pkg;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database extends JFrame {
    private JPanel contentPane;
    private JButton btnCancel;
    private JButton btnAddtoDatabase;
    private JButton btnAdd_Entry;
    private JTable tblDataset;
    private JTextField tfXaxis;
    private JTextField tfYaxis;
    private JButton btnOpendatabase;
    private DefaultTableModel model;
    static JFrame database;


    public Database() {
        super("Database/pkg");
        tblDataset.getTableHeader().setReorderingAllowed(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setResizable(false);
        createtable();
        contentPane.setPreferredSize(new Dimension(350, 530));
        btnAddtoDatabase.addActionListener(e -> {

            try {
                String url = "jdbc:sqlserver://DMC\\MSSQLSERVER:1433;database=master";
                String user = "Diego";
                String pass = "mrman1091";
                DriverManager.registerDriver(new SQLServerDriver());
                Connection conn = DriverManager.getConnection(url, user, pass);

//          stmt = conn.createStatement();

                DefaultTableModel tblMODEL = (DefaultTableModel) tblDataset.getModel();
                if (tblMODEL.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Table must not be empty", "Empty Table", JOptionPane.ERROR_MESSAGE);
                } else {
                    String x;
                    String y;


                    for (int i = 0; i < tblMODEL.getRowCount(); i++) {
                        x = tblMODEL.getValueAt(i, 0).toString();
                        y = tblMODEL.getValueAt(i, 1).toString();


                        String query = "insert into [Database](X,Y) values(?,?)";
                        PreparedStatement statement = conn.prepareStatement(query);
                        statement.setString(1, x);
                        statement.setString(2, y);
                        statement.execute();
                    }
                }
                JOptionPane.showMessageDialog(null, "Table has been inserted Sucessfully", "INSERT", JOptionPane.INFORMATION_MESSAGE);

                tblMODEL.setRowCount(0);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        btnCancel.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) tblDataset.getModel();
            model.setRowCount(0);

        });


        contentPane.registerKeyboardAction(e -> onAdd_Entry(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        btnAdd_Entry.addActionListener(e -> onAdd_Entry());
        btnOpendatabase.addActionListener(e -> Createdatabase(database));


    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            database = new Database();
            database.pack();
            database.setLocationRelativeTo(null);
            database.setVisible(true);
        });


    }

    public void Createdatabase(JFrame database) {
        Database_Form dialog = new Database_Form(database);
        dialog.pack();
        dialog.setLocationRelativeTo(database.getContentPane());
        dialog.setVisible(true);

    }


    private void createtable() {
        String[] column = new String[]{"X", "Y"};
        this.model = new DefaultTableModel(null, column);
        tblDataset.setModel(this.model);


//        String nextRowId = Integer.toString(model.getRowCount());


        TableColumnModel columns = tblDataset.getColumnModel();
        columns.getColumn(0).setMinWidth(75);
        columns.getColumn(1).setMinWidth(75);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer = (DefaultTableCellRenderer) tblDataset.getTableHeader().getDefaultRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        centerRenderer.setHorizontalAlignment(JLabel.HORIZONTAL);
        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(1).setCellRenderer(centerRenderer);
    }


    private void onAdd_Entry() {
        if (tfYaxis.getText().equals("") && tfYaxis.getText().equals("")) {
            JOptionPane.showMessageDialog(contentPane, "Enter number in fields", "Enter Data", JOptionPane.ERROR_MESSAGE);
        } else {
            Object[] row = new Object[]{tfXaxis.getText(), tfYaxis.getText()};
            this.model.addRow(row);
            tfXaxis.setText("");
            tfYaxis.setText("");
        }
    }

}








package Regression_Analysis.pkg;

import org.sqlite.JDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

import static java.sql.DriverManager.getConnection;


public class Regression_Analysis extends JFrame {
    public static Regression_Analysis Regression_Analysis;
    //    public final String Connection_String = "jdbc:sqlite:database.db";
    public final String Connection_String = "jdbc:sqlite::memory:";
    public JTable tblDataset;
    public Vector<String> Datasetlist;
    public ArrayList<String> List_Series_Name;
    private JButton btnGenerate;
    private JButton buttonCancel;
    private JFormattedTextField tfXaxis;
    private JFormattedTextField tfYaxis;
    private JButton btnAdd_Entry;
    private JPanel contentPane;
    private JPanel mainPanel;
    private JComboBox<String> cmbDatasetList;
    private JComboBox<String> cmbSeriesList;
    private JButton btnAddSeries;
    private JButton btnAddDataset;
    private DefaultTableModel model;
    private String Combo_Option;
    private int counter;
    private String[] Series_arr;
    private String Series_N;

    public Regression_Analysis() {
        super("Regression Analysis");
        setMinimumSize(new Dimension(335, 635));
        setContentPane(mainPanel);
        listeners();
        disableComponents();
        createtable();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Resources/Image/007-boar.png")));
        setIconImage(icon.getImage());
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JDialog.setDefaultLookAndFeelDecorated(true);
        Regression_Analysis = new Regression_Analysis();

        Regression_Analysis.pack();
        Regression_Analysis.setLocationRelativeTo(null);
        Regression_Analysis.setDefaultCloseOperation(3);
        Regression_Analysis.setVisible(true);
    }

    public void disableComponents() {
        btnAdd_Entry.setEnabled(false);
        btnAddSeries.setEnabled(false);
        btnGenerate.setEnabled(false);
        btnAdd_Entry.setEnabled(false);
        getRootPane().setDefaultButton(btnAdd_Entry);
    }

    public void listeners() {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dumptables();
            }
        });

        btnGenerate.addActionListener((e) -> {
            onGenerate();
        });

        buttonCancel.addActionListener((e) -> {
            onCancel();
        });

        contentPane.registerKeyboardAction((e) -> {
            onCancel();
        }, KeyStroke.getKeyStroke(27, 0), 1);

        btnAdd_Entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd_Entry();
            }
        });

        cmbDatasetList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cmbDataset_x = (JComboBox) e.getSource();
                Combo_Option = (String) cmbDataset_x.getSelectedItem();
            }
        });

        btnAddSeries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tblDataset.getRowCount() >= 2) {
                    insertSeriesName();

                    int x = JOptionPane.showConfirmDialog(Regression_Analysis, "Create Series?", "Series", 2, 3);
                    if (x == 0) {
                        ++counter;
                        String Series_Name = JOptionPane.showInputDialog(Regression_Analysis, "Enter Series", "Series Name", 3);
                        cmbSeriesList.setEnabled(true);
                        cmbSeriesList.addItem(Series_Name);
                        model.setRowCount(0);

                        btnGenerate.setEnabled(true);
                        JOptionPane.showMessageDialog(Regression_Analysis, "table created");
                    }

                } else {
                    JOptionPane.showMessageDialog(Regression_Analysis, "Table must be filled before creating a series");
                }

            }
        });

        btnAddDataset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Dataset = JOptionPane.showInputDialog(Regression_Analysis, "Dataset Name:", "Dataset");
                if (Dataset != null && Dataset.length() > 0) {
                    insertDatalist(Dataset);
                    cmbDatasetList.addItem(Dataset);
                    btnAddSeries.setEnabled(true);
                    btnAdd_Entry.setEnabled(true);
                    cmbDatasetList.setEnabled(true);
                }
            }
        });
    }

    public void insertSeriesName() {
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT name from sqlite_master where type='table' and name = 'All_Series'");
            if (!res.next()) {
                stmt.execute("create table All_Series(_id INT default 1,_X  DOUBLE precision,_Y  DOUBLE precision)");
//                stmt.execute("create table Datalist(Dataset_Name TEXT not null,'Group Name' INTEGER primary key autoincrement)");
            }
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO All_Series VALUES (?,?,?)");
            preparedStatement.setDouble(1, this.counter);
            double x;
            double y;


            for (int i = 0; i < tblDataset.getRowCount(); i++) {
                x = Double.parseDouble(tblDataset.getValueAt(i, 0).toString());
                y = Double.parseDouble(tblDataset.getValueAt(i, 1).toString());


                String query = "insert into All_Series values(?,?,?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setDouble(1, this.counter);
                statement.setDouble(2, x);
                statement.setDouble(3, y);
                statement.executeUpdate();
            }

            res.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDatalist(String Dataset) {
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT name from sqlite_master where type='table' and name = 'Datalist'");
            if (!res.next()) {
                stmt.execute("create table Datalist(Dataset_Name TEXT not null,'Group Name' INTEGER primary key autoincrement)");
            }
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Datalist(Dataset_Name) VALUES(?)");
            pstmt.setString(1, Dataset);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void insertAll_Series(double _X, double _Y) {

        try {
            String sql = "INSERT INTO All_Series VALUES(?,?,?)";

            Connection conn = this.connect();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, this.counter);
            pstmt.setDouble(2, _X);
            pstmt.setDouble(3, _Y);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void createtable() {
        String[] column = new String[]{"X", "Y"};
        this.model = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDataset.setModel(model);
        TableColumnModel columns = tblDataset.getColumnModel();
        columns.getColumn(0).setMinWidth(100);
        columns.getColumn(1).setMinWidth(100);
        new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = (DefaultTableCellRenderer) this.tblDataset.getTableHeader().getDefaultRenderer();
        centerRenderer.setHorizontalAlignment(0);
        centerRenderer.setHorizontalAlignment(0);
        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        tblDataset.getTableHeader().setReorderingAllowed(false);

    }

    private void onGenerate() {
        Chart_Title chart_title = new Chart_Title(Regression_Analysis);
        chart_title.pack();
        chart_title.setLocationRelativeTo(Regression_Analysis);
        chart_title.setVisible(true);
    }

    private void onCancel() {
        int choice = JOptionPane.showConfirmDialog(Regression_Analysis, "Delete Table?", "Erase Table", 2, 3);
        if (choice == 0) {
            this.model.setRowCount(0);
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new JDBC());
            conn = getConnection(Connection_String);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void dumptables() {
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();

//            stmt.execute("delete table  All_Series;");
            stmt.execute("DROP TABLE IF EXISTS All_Series");
            stmt.execute("DROP TABLE IF EXISTS Series_Information");
            stmt.execute("DROP TABLE IF EXISTS Datalist");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void onAdd_Entry() {
        if (!tfXaxis.getText().isEmpty() && !tfYaxis.getText().isEmpty()) {
//            insertAll_Series(Double.parseDouble(tfXaxis.getText()), Double.parseDouble(tfYaxis.getText()));
            Object[] row = new Object[]{Double.parseDouble(tfXaxis.getText()), Double.parseDouble(tfYaxis.getText())};
            model.addRow(row);
            tfXaxis.setText("0");
            tfYaxis.setText("0");
        } else {
            JOptionPane.showMessageDialog(Regression_Analysis, "Enter number in fields", "Enter Data", 0);
        }

    }

    public void createUIComponents() {
        contentPane = new JPanel();
        contentPane.setMinimumSize(new Dimension(320, 600));
        contentPane.setPreferredSize(new Dimension(320, 600));
        contentPane.setMaximumSize(new Dimension(320, 600));
        cmbSeriesList = new JComboBox();
        cmbSeriesList.setEnabled(false);
        cmbDatasetList = new JComboBox();
        cmbDatasetList.setEnabled(false);
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setGroupingUsed(false);
        tfXaxis = new JFormattedTextField(amountFormat);
        tfXaxis.setHorizontalAlignment(0);
        tfYaxis = new JFormattedTextField(amountFormat);
        tfYaxis.setHorizontalAlignment(0);
    }
}

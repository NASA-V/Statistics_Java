package Regression_Analysis.pkg;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Chart_Title extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfChart_title;
    private JTextField tfY_Axis;
    private JTextField tfX_Axis;
    private JTextField tfSeriesName;
    private JTextField tfDatasetName;
    public ArrayList<Chart_Info> Chart_Information;

    public Chart_Title(Regression_Analysis frame) {
        super(frame, true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK(frame));
        buttonCancel.addActionListener(e -> onCancel());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void onOK(Regression_Analysis frame) {


        String title, xaxis, yaxis, Series_Name;
        title = tfChart_title.getText();
        xaxis = tfX_Axis.getText();
        yaxis = tfY_Axis.getText();
        Series_Name = tfSeriesName.getText();
        String[] info = new String[5];
        info[0] = title;
        info[1] = xaxis;
        info[2] = yaxis;
        info[3] = Series_Name;
        info[4] = tfDatasetName.getText();
        String x = tfDatasetName.getText();


        frame.Datasetlist.add(info[4]);
//        frame.createUIComponents();


//        Regression_Analysis.list.add(info[4]);
        String[] petStrings = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};


//        frame.cmbDatasetList.setModel(new DefaultComboBoxModel<String>(combo));


        if (frame.tblDataset.getRowCount() == 0) {
            dispose();
        } else {
            dispose();
/*
            try {
                DriverManager.registerDriver(new JDBC());
                Connection conn = DriverManager.getConnection("jdbc:sqlite:Databases/Database.db");
                Statement statement = conn.createStatement();


                statement.execute("Create table IF NOT EXISTS Dataset" +
                        "(_id INTEGER PRIMARY KEY, Datasets text not null)");

                String query = "insert into Dataset(X) values(?)";

                PreparedStatement statement1 = conn.prepareStatement(query);

                statement1.setString(1, x);
                statement1.execute();


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }*/


            dispose();
            Scatterplot plot = new Scatterplot(frame, true, "Regression Analysis", info);

            plot.pack();
            plot.setLocationRelativeTo(frame);
            plot.setVisible(true);

            Chart_Information = new ArrayList<>();
            Chart_Information.add(new Chart_Info(title, xaxis, yaxis, Series_Name));

        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}




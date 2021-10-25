package Regression_Analysis.pkg;

public class Chart_Info {
    private String Title;
    private String X_Axis;
    private String Y_Axis;
    private String Series_Name;
    private String[] Chart_info;

    public Chart_Info(String title, String x_Axis, String y_Axis, String series_Name) {
        Chart_info = new String[4];

        Chart_info[0] = title;
        Chart_info[1] = x_Axis;
        Chart_info[2] = y_Axis;
        Chart_info[3] = series_Name;


    }
}

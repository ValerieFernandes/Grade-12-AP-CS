import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class TeacherHomePanel extends JPanel{

    /** JComboBox for displaying courses and their students */
    private JComboBox courseList, studentList;
    /** JButtons for creating aa course and quitting the program */
    private JButton createCourseButton, quitButton;
    /** a field for entering the name of a new course */
    private JTextField newCourseName;
    /** the teacher whose profile is being displayed */
    private Teacher teacher;
    /** JFreeChart to display score history */
    private JFreeChart chart;
    /** a listener for updating the student who is being graphed */
    private ActionListener updateStudentListener;

    TeacherHomePanel(ActionListener createCourseListener, ActionListener quitListener, ActionListener updateCourseListener, ActionListener updateStudentListener, Teacher teacher){

        this.teacher = teacher;
        this.updateStudentListener = updateStudentListener;
        this.setBackground(new Color(197, 237, 204));
        this.setLayout(null);

        XYSeriesCollection data = new XYSeriesCollection(); // make datasets from student info
        this.chart = ChartFactory.createXYLineChart("Graph", "Session", "Rating", data);
        formatChart(); // format chart to increase visibility
        ChartPanel myChart = new ChartPanel(chart); // create a panel on which to display the chart
        myChart.setPreferredSize(new java.awt.Dimension(400, 150));
        myChart.setBounds(400, 150, 300, 300);

        createCourseButton = new JButton("Create Course");
        createCourseButton.addActionListener(createCourseListener);
        createCourseButton.setBounds(160, 150, 160, 30);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(quitListener);
        quitButton.setBounds(250, 450, 60, 30);

        JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("serif", Font.PLAIN, 40));
        titleLabel.setBounds(350, 25, 100, 50);

        JLabel createLabel = new JLabel("New course");
        createLabel.setBounds(25, 100, 100, 30);

        newCourseName = new JTextField(1);
        newCourseName.setBounds(160, 100, 160, 30);

        courseList = new JComboBox(teacher.getCourseInfo());
        courseList.setBounds(160, 275, 160, 30);
        courseList.addActionListener(updateCourseListener);

        studentList = new JComboBox();
        studentList.setBounds(160, 325, 160, 30);
        studentList.addActionListener(updateStudentListener);

        this.add(createCourseButton);
        this.add(myChart);
        this.add(quitButton);
        this.add(titleLabel);
        this.add(createLabel);
        this.add(newCourseName);
        this.add(courseList);
        this.add(studentList);
    }

    /** getNewCourseName
     * gets and returns the entered name in the new course field
     * @return a String representing the title of the course
     */
    public String getNewCourseName(){
        String name = this.newCourseName.getText();
        this.newCourseName.setText("");
        return name;
    }

    /** getStudentName
     * gets and returns the name chosen from the student list
     * @return a String representing a student's name
     */
    public String getStudentName(){
        return (String) this.studentList.getSelectedItem();
    }

    /** getCourseName
     * gets and returns the name chosen from the course list
     * @return a String representing the course name
     */
    public String getCourseName(){
        return (String) this.courseList.getSelectedItem();
    }

    /** updateCourseList
     * adds a new course to the course list
     * @param title a String representing the title of the course
     */
    public void updateCourseList(String title){
        this.courseList.addItem(title);
    }

    /** updateStudentList
     * creates a new comboBox displaying a new set of students
     * @param list a String reprsenting the lis of student names separated by spaces
     */
    public void updateStudentList(String list){

        this.remove(studentList);
        this.studentList = new JComboBox(list.split(" "));
        this.studentList.setBounds(160, 325, 160, 30);
        this.studentList.addActionListener(this.updateStudentListener);
        this.add(studentList);
        this.repaint();
        this.revalidate();
    }

    /** createDataset
     * develops a data set based on the student's score history
     * @param student a Student whose information will be contained in the dataset
     * @return an XYSeries collection representing the data to be displayed
     */
    private XYSeriesCollection createDataset(Student student){
        XYSeries vocab = new XYSeries("vocab");
        XYSeries structure = new XYSeries("structure");
        XYSeries verb = new XYSeries("verb");

        ArrayList<double[]> data = student.getLevels();

        int count = 1;
        for(double[] session: data){
            System.out.println(Arrays.toString(session));
            vocab.add(count, session[0]);
            structure.add(count, session[1]);
            verb.add(count, session[2]);
            count++;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(vocab);
        dataset.addSeries(structure);
        dataset.addSeries(verb);
        return dataset;
    }

    /** formatChart
     * formats the line graph axis, shapes, and colours
     */
    public void formatChart(){
        XYPlot chartPlot = (XYPlot) this.chart.getPlot();
        ((NumberAxis)chartPlot.getDomainAxis()).setNumberFormatOverride(new DecimalFormat("0.0"));
        ((NumberAxis)chartPlot.getRangeAxis()).setNumberFormatOverride(new DecimalFormat("0.00"));
        ((NumberAxis)chartPlot.getRangeAxis()).setRange(0,10);

        chartPlot.getRenderer().setSeriesPaint(0, new Color(36, 81, 158));
        chartPlot.getRenderer().setSeriesPaint(1, new Color(181, 54, 38));
        chartPlot.getRenderer().setSeriesPaint(2, new Color(37, 156, 43));
        chartPlot.setBackgroundPaint(Color.lightGray);
        chartPlot.setDomainGridlinePaint(Color.white);
        chartPlot.setRangeGridlinePaint(Color.white);

        ((XYLineAndShapeRenderer)chartPlot.getRenderer()).setBaseShapesVisible(true);
        ((XYLineAndShapeRenderer)chartPlot.getRenderer()).setBaseShapesFilled(true);

        chartPlot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        chartPlot.setDomainCrosshairVisible(true);
        chartPlot.setRangeCrosshairVisible(true);
    }

    /** updateGraph
     * updates the graph display to include the name and information of the student
     * @param student a Student to which the graph is being changed
     */
    public void updateGraph(Student student){
        chart.setTitle(student.getUsername());
        chart.getXYPlot().setDataset(createDataset(student));
        this.repaint();
        this.revalidate();
    }
}

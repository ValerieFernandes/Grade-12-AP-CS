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
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/** StudentHomePanel
 * Represents a panel which displays a Student's home screen content
 * @author Valerie Fernandes
 */
public class StudentHomePanel extends JPanel{

    /** JButtons for starting a practice session, quitting, and joining a course */
    private JButton startButton, quitButton, courseButton;
    /** JField to enter a course name */
    private JTextField courseName;
    /** JFreeChart to display score history */
    private JFreeChart chart;
    /**JLabel for course join failure */
    private JLabel failureLabel;
    /** Student whose information id being displayed */
    private Student student;
    /** The data series that are displayed in the chart */
    private XYSeries vocab, structure, verb;

    /** StudentHomePanel
     * Creates a student home panel, with the desired Student and listeners
     * @param startListener listener to astart a practice session
     * @param quitListener listener to quit the user account
     * @param joinListener listener to join a new course
     * @param student the student whose profile is being displayed
     */
    StudentHomePanel(ActionListener startListener, ActionListener quitListener, ActionListener joinListener, Student student){

        this.student = student;
        this.setBackground(new Color(197, 237, 204));
        this.setLayout(null);

        XYSeriesCollection data = createDataset(); // make datasets from student info
        this.chart = ChartFactory.createXYLineChart(student.getUsername(), "Session", "Rating", data);
        formatChart(); // format chart to increase visibility
        ChartPanel myChart = new ChartPanel(chart); // make a panel of the chart so it can be part of the GUI
        myChart.setPreferredSize(new java.awt.Dimension(400, 150));
        myChart.setBounds(400, 150, 300, 300);

        courseButton = new JButton("Join Course");
        courseButton.addActionListener(joinListener);
        courseButton.setBounds(160, 170, 160, 30);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(quitListener);
        quitButton.setBounds(250, 440, 60, 30);

        startButton = new JButton("Start Session");
        startButton.addActionListener(startListener);
        startButton.setBounds(160, 295, 160, 30);

        JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("serif", Font.PLAIN, 40));
        titleLabel.setBounds(350, 25, 100, 50);

        JLabel createLabel = new JLabel("New course");
        createLabel.setBounds(25, 120, 100, 30);

        failureLabel = new JLabel("");
        failureLabel.setBounds(160, 210, 200, 30);

        courseName = new JTextField(1);
        courseName.setBounds(160, 120, 160, 30);

        this.add(failureLabel);
        this.add(myChart);
        this.add(titleLabel);
        this.add(startButton);
        this.add(quitButton);
        this.add(courseButton);
        this.add(createLabel);
        this.add(courseName);
    }

    /** getCourseName
     * gets and returns the name in the courseName text field
     * @return a String representing a course to be joined
     */
    public String getCourseName(){
        String name = this.courseName.getText();
        this.courseName.setText("");
        return name;
    }

    /** createDataset
     * develops a data set based on the student's score history
     * @return an XYSeries collection representing the data to be displayed
     */
    private XYSeriesCollection createDataset(){
        this.vocab = new XYSeries("vocab");
        this.structure = new XYSeries("structure");
        this.verb = new XYSeries("verb");

        ArrayList<double[]> data = this.student.getLevels();

        int count = 1;
        for(double[] session : data){
            this.vocab.add(count, session[0]);
            this.structure.add(count, session[1]);
            this.verb.add(count, session[2]);
            count++;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(this.vocab);
        dataset.addSeries(this.structure);
        dataset.addSeries(this.verb);
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
     * updates the graph display to include the given information
     * @param scores a double array representing the scores to add to the graph
     */
    public void updateGraph(double[] scores){

        int session = this.student.getLevels().size();
        this.vocab.add(session, scores[0]);
        this.structure.add(session, scores[1]);
        this.verb.add(session, scores[2]);
        this.repaint();
        this.revalidate();
    }

    /** setFailureLabel
     * updates the failure label to inform user of course join status
     * @param message String representing the failure message
     */
    public void setFailureLabel(String message){
        this.failureLabel.setText(message);
    }

}

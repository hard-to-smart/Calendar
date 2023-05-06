import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.management.MBeanNotificationInfo;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.colorchooser.ColorChooserComponentFactory;

public class CalendarApp extends JFrame implements ActionListener{
    /**
     * 
     */
    private GregorianCalendar calendar;
    private JLabel currentMonthLbl, currentTimeLbl;
    private JComboBox<String> monthCb, yearCb;
    private JButton prevBtn, nextBtn;
    private JPanel calendarPnl; 

    
    public CalendarApp(){
        super("Calendar");

        setSize(500,500);
        setVisible(true);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //initializing calendar
        
       calendar = new GregorianCalendar();            
        //creating components in the calendar
        int currentYr=calendar.get(Calendar.YEAR);
        int currentMnth=calendar.get(Calendar.MONTH);
        
        currentMonthLbl= new JLabel();
        currentTimeLbl= new JLabel();
        monthCb=new JComboBox<String>(getMnthNames());
        monthCb.setSelectedIndex(currentMnth);
        monthCb.setBackground(new Color(26, 25, 255));
        yearCb= new JComboBox<String>(getYrNames());
        yearCb.setSelectedItem(String.valueOf(currentYr));
        yearCb.setBackground(new Color(26, 25, 255));
        prevBtn=new JButton("<<");
        prevBtn.setBackground(new Color(26, 25, 255));
        nextBtn=new JButton(">>");
        nextBtn.setBackground(new Color(26, 25, 255));
        calendarPnl=new JPanel(new GridLayout(0,7));
       calendarPnl.setSize(500,500);
        calendarPnl.setVisible(true);
       calendarPnl.setBackground(new Color(164, 208, 164));
        
        

        calendarPnl.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
        //Setting the state of calendar
        updateCalendar();

        //adding the components to the pane
        Container contentsPane= getContentPane();
        contentsPane.setLayout(new BorderLayout());
        
        contentsPane.setBackground(new Color(182, 234, 250));
        JPanel controlPnl= new JPanel(new FlowLayout());
        controlPnl.setBackground(new Color(182, 234, 250));
        //adding elements in control panel
        controlPnl.add(monthCb);
        controlPnl.add(yearCb);
        controlPnl.add(prevBtn);
        controlPnl.add(nextBtn);
        controlPnl.setForeground(new Color(160, 216, 179));
       
        //adding control panel in contentsPane space
        contentsPane.add(currentMonthLbl, BorderLayout.NORTH);
        contentsPane.add(controlPnl, BorderLayout.CENTER);
        contentsPane.add(calendarPnl, BorderLayout.SOUTH);
        contentsPane.add(currentTimeLbl, BorderLayout.EAST);

        //adding event listeners
       monthCb.addActionListener(this);
        yearCb.addActionListener(this);
        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        //displaying window
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        }

        private void updateCalendar(){
            //places the month and the year on the title bar
           
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.US);
            currentMonthLbl.setText(monthFormat.format(calendar.getTime()));
            currentMonthLbl.setHorizontalAlignment(SwingConstants.CENTER); 
            currentMonthLbl.setFont(new Font("Monospaced", Font.BOLD, 20));

        //adding elements to calendar panel
        calendarPnl.removeAll();
        String[] weekDays= {"SUN","MON","TUE","WED","THURS", "FRI", "SAT"};
        for(String DayOfWeek : weekDays){
            JLabel weekDayLbl= new JLabel(DayOfWeek, JLabel.CENTER);
            weekDayLbl.setOpaque(true);
            weekDayLbl.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            weekDayLbl.setBackground(new Color(245, 243, 193));
            weekDayLbl.setForeground(new Color(97, 122, 85));
            calendarPnl.add(weekDayLbl);
        }

   
    int dayOfMnth=1;
    int dayOfWk=calendar.get(Calendar.DAY_OF_WEEK);
    int noOfRows= (int)Math.ceil((dayOfWk + calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1)/7.0);
    for(int row=0; row< noOfRows;row++){
        for(int col=0; col<7;col++){
            JButton buttonDay= new JButton();
            buttonDay.setFocusPainted(false);
            buttonDay.setBorder(BorderFactory.createEtchedBorder());
            buttonDay.setBackground(new Color(252, 255, 178));
            buttonDay.setForeground(Color.BLACK);
            
            if(row==0 && col<dayOfWk-1){
                buttonDay.setEnabled(false);
            }
            else if(dayOfMnth> calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
                buttonDay.setEnabled(false);
            }
            else{
                buttonDay.setText(Integer.toString(dayOfMnth));
                dayOfMnth++;
            }
            calendarPnl.add(buttonDay);
        }
    }
    calendarPnl.validate();
    calendarPnl.repaint();

}   
private String[] getMnthNames(){
    String[] mnthName= new String[12];
    for(int i=0;i<12;i++){
        SimpleDateFormat monthFormat= new SimpleDateFormat("MMMM", Locale.US);
        GregorianCalendar monthCalendar= new GregorianCalendar(2023, i, 1);
        mnthName[i]=monthFormat.format(monthCalendar.getTime());
    }
    return mnthName;
}
private String[] getYrNames(){
    String[] yrNames=new String[101];
    for(int i=0;i<101;i++){
        yrNames[i]= String.valueOf(calendar.get(Calendar.YEAR)-50+i);
    }
    return yrNames;
}

@Override
public void actionPerformed(ActionEvent e){
    Object sourceObj= e.getSource();
    if(sourceObj==monthCb|| sourceObj==yearCb){
        int yr= Integer.parseInt((String)yearCb.getSelectedItem());
        int mnth= Arrays.asList(getMnthNames()).indexOf(monthCb.getSelectedItem());
        calendar= new GregorianCalendar(yr, mnth, 1);
        updateCalendar();
    }
    else if(sourceObj==prevBtn){
        calendar.add(Calendar.MONTH,-1);
        updateCalendar();
    }
    else if(sourceObj==nextBtn){
        calendar.add(Calendar.MONTH,+1);
        updateCalendar();
}
}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
        new CalendarApp();
        }
        });
}
}
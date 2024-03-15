import java.awt.*;
import java.awt.event.*;

public class MonthlyCalendar extends Frame {
    Label mt, yr, dates[][];
    Choice month;
    Scrollbar year;
    TextField yrText;
    Panel calenPanel;
    int numDays[]={31,28,31,30,31,30,31,31,30,31,30,31};
    int height=400;
    MonthlyCalendar()
    {
        this.setLayout(new BorderLayout(10,10));
        this.addWindowListener(new Close());

        mt=new Label("Month");
        month=new Choice();
        String months[]={"Jan", "Feb", "Mar","April","May","June","July","Aug","Sept","Oct","Nov","Dec"};
        for (int i = 0; i < 12; i++) {
            month.add(months[i]);
        }
        month.select(2);
        month.addItemListener(new ShowChoice());

        Panel mtPanel=new Panel(new GridLayout(1,4,10,10));
        mtPanel.add(new Label());
        mtPanel.add(mt);
        mtPanel.add(month);
        mtPanel.add(new Label());

        yr=new Label("Year");
        yrText=new TextField();
        yrText.setEditable(false);
        year=new Scrollbar(0,2024,0,1900,2201);
        yrText.setText(year.getValue()+"");
        year.addAdjustmentListener(new Scroll());
        Panel yrPanel =new Panel(new GridLayout(1,5,10,10));
        yrPanel.add(new Label());
        yrPanel.add(yr);
        yrPanel.add(year);
        yrPanel.add(yrText);
        yrPanel.add(new Label());

        calenPanel = new Panel(new GridLayout(7,7,10,10));
        calenPanel.setBackground(Color.gray);
        dates=new Label[7][7];
        String day[]={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        for (int i = 0; i < 7; i++) {
            dates[0][i]=new Label(day[i]);
            calenPanel.add(dates[0][i]);
        }
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                dates[i][j]=new Label();
                calenPanel.add(dates[i][j]);
            }
        }

        add(mtPanel,BorderLayout.NORTH);
        add(calenPanel,BorderLayout.CENTER);
        add(yrPanel,BorderLayout.SOUTH);

    }
    public static void main(String[] args) {
        MonthlyCalendar obj=new MonthlyCalendar();
        obj.setVisible(true);
        obj.setSize(400, obj.height);
        obj.showCalendar();
    }
    public  Insets getInsets()
    {
        return new Insets(50,20,20,20);
    }
    public void showCalendar()
    {

        System.out.println(month.getSelectedIndex()+" "+year.getValue());
        int yrD,mtD,i,s=0,days;
        yrD=year.getValue();
        mtD=month.getSelectedIndex();
        int firstDay=getFirstWeekDay(mtD,yrD);
        System.out.println(firstDay);
        int lastDay=numDays[mtD];
        if (isLeapYear(yrD) && mtD==1)
            lastDay=29;

        days=1;
        int flag=0;
        for(i=1;i<7;i++)
        {
            for (int j = 0; j < 7; j++) {
                if(days==1 && j<firstDay){
                    dates[i][j].setText("");
                    flag=1;
                }
                else if (flag==1 && days<=lastDay) {
                    dates[i][j].setText(days+"");
                    days++;
                }
                else {
                    dates[i][j].setText("");
                }
            }
        }
    }
    private int getFirstWeekDay(int mtD, int yrD){
        int i,s=0;
        for (i = 1900; i < yrD; i++) {
            if(isLeapYear(i))
                s=s+366;
            else
                s=s+365;
        }
        for(i=0;i<mtD;i++)
        {
            s=s+numDays[i];
        }
        if(isLeapYear(yrD) && mtD>1) {
            s = s + 1;
            System.out.println("leap");
        }
        return s%7;
    }
    private boolean isLeapYear(int n){
        return (n % 4 == 0 && n % 100 != 0) || n % 400 == 0;
    }
    class Close implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
    class Scroll implements AdjustmentListener{

        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            yrText.setText(year.getValue()+"");
            showCalendar();
        }
    }
    class ShowChoice implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            showCalendar();
        }
    }
}
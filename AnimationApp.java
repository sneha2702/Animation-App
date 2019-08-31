import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Collections;
import java.util.Vector;

public class AnimationApp extends JFrame implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;
    AnimationPanel ap=new AnimationPanel();
    private JButton startAnimation=new JButton("Start");
    private JButton pauseAnimation=new JButton("Pause");
    private JButton stopAnimation=new JButton("Stop");
    private JList animationObj;

    private AnimationApp(String title){
        super(title);
    }

    private void createAndShowGUI(){
        readFile();
        initComponents();
        animationObj.addListSelectionListener(this);
        startAnimation.addActionListener(this);
        pauseAnimation.addActionListener(this);
        stopAnimation.addActionListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
    private void initComponents(){
        JPanel mainPanel=new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(620,620));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(ap,BorderLayout.CENTER);
        mainPanel.add(animationObj,BorderLayout.WEST);
        JPanel flowLayout=new JPanel();
        flowLayout.setLayout(new FlowLayout());
        flowLayout.add(startAnimation);
        flowLayout.add(pauseAnimation);
        flowLayout.add(stopAnimation);
        stopAnimation.setEnabled(false);
        pauseAnimation.setEnabled(false);
        stopAnimation.setEnabled(false);
        mainPanel.add(flowLayout,BorderLayout.SOUTH);
        add(mainPanel,BorderLayout.CENTER);

    }
    private void readFile(){
        Vector<Animation> names=new Vector();
        try{
            File file=new File(".\\Animations\\animations.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                String[] line = st.split(":");
                //System.out.println(st);
                String name = line[0].replace(' ', '_');
                Animation a=new Animation(name,Integer.parseInt(line[1]),Integer.parseInt(line[2]),Integer.parseInt(line[3]),Integer.parseInt(line[4]));
                names.add(a);
            }
            Collections.sort(names, new Compartor());
            animationObj= new JList(names);
            animationObj.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void valueChanged(ListSelectionEvent e){
        ap.stopAnimation();
        Animation a = (Animation) animationObj.getSelectedValue();
        ap.loadAnimation(a);
        startAnimation.setEnabled(true);
        pauseAnimation.setEnabled(false);
        stopAnimation.setEnabled(false);

    }
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==startAnimation){
            ap.startAnimation();
            startAnimation.setEnabled(false);
            pauseAnimation.setEnabled(true);
            stopAnimation.setEnabled(true);
        }
        if (ae.getSource()== stopAnimation) {
            ap.stopAnimation();
            startAnimation.setEnabled(true);
            pauseAnimation.setEnabled(false);
            stopAnimation.setEnabled(false);
            pauseAnimation.setText("Pause");
        }
        if(ae.getActionCommand()=="Pause"){
            ap.pauseAnimation();
            pauseAnimation.setText("Resume");
        }
        if(ae.getActionCommand()=="Resume"){
            ap.resumeAnimation();
            pauseAnimation.setText("Pause");
        }

    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
                | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            AnimationApp mainFrame=new AnimationApp("Animation");
            mainFrame.createAndShowGUI();

        });

    }

}

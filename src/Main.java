import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;
import java.util.List;


public class Main {
    public static JButton choilai;
    public static int counter = 0;
    private static Map<JButton,Boolean> clickmap = new HashMap<>();
    private static int ClickCount = 0;
    private static ArrayList<File> listImage;
    public static List<JButton> boderjbut = new ArrayList<>();
   private static boolean clicked = false ;
    public static JProgressBar jProgressBar ;
    private static JPanel con2;


    public static void main(String[] args) {
        getList();
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setBackground(Color.pink);
        frame.setSize(1200, 700);
        frame.setResizable(false);
        frame.setTitle("Pikachu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        frame.add(main);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());

        choilai = new JButton("Chơi Lại");
        jPanel1.add(Box.createHorizontalStrut(10));
        jPanel1.add(choilai);
        jPanel1.add(Box.createHorizontalStrut(30));
        jProgressBar = new JProgressBar();
        jPanel1.add(jProgressBar);


        JPanel jPanel2 = new JPanel();
        con2 = new JPanel();
        con2.setBackground(Color.GRAY);
        con2.setLayout(new GridLayout(8,16,1,1));

        jPanel2.add(con2);

        int index = 0; // Biến đếm chỉ số trong listImage
        for (int i = 0; i < 128; i++) {
           JButton Jbut = new JButton();
            con2.add(Jbut);
            Jbut.setPreferredSize(new Dimension(60,60));
            Jbut.setName(listImage.get(index).getName());

            File imageFile = listImage.get(index);
            String imagePath = imageFile.getPath();
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image resize = imageIcon.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(resize);
            Jbut.setIcon(imageIcon);
            index++;


            Jbut.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    clickmap.put(Jbut,false);
                    clicked = clickmap.get(Jbut); // lấy ra trạng thái của Jbut

                    System.out.println("Trạng thái ban đầu: "+ clicked);
                    if (ClickCount < 2 && clicked == false) {
                        Jbut.setBorder(new LineBorder(Color.RED, 3));
                        clickmap.put(Jbut, true);
                        clicked = clickmap.get(Jbut);
                        System.out.println("hiện tại: " + clicked);
                        ClickCount++;
                        System.out.println(ClickCount);
                        System.out.println(Jbut.getName());
                        boderjbut.add(Jbut);
                    } else if (clicked == true) {
                        Jbut.setBorder(null);
                        clickmap.put(Jbut, false);
                        clicked = clickmap.get(Jbut);
                        ClickCount--;
                        boderjbut.remove(Jbut);
                    }

                    System.out.println("size: " + boderjbut.size());
                    if (boderjbut.size() == 2) {
                        JButton btn1 = boderjbut.get(0);
                        JButton btn2 = boderjbut.get(1);
                        if (btn1.getName().equals(btn2.getName())) {
                            btn1.setIcon(null);
                            btn2.setIcon(null);
                            btn1.setBorder(null);
                            btn2.setBorder(null);
                            boderjbut.clear();
                            System.out.println("Đã Xóa");
                            ClickCount = 0;
                            for (JButton button: boderjbut) {
                                clickmap.remove(button);
                            }
                            Timer timer = new Timer(1000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    counter-=3;

                                }
                            });
//                            timer.setRepeats(false);
                            timer.start();
                        }
                        else if(!btn1.getName().equals(btn2.getName())){
                            btn1.setBorder(null);
                            btn2.setBorder(null);
                            boderjbut.clear();
                            System.out.println("Đã xóa các phần tử khỏi mảng");
                            System.out.println("Size: " + boderjbut.size());
                            ClickCount = 0;
                        }

                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                    public void mouseExited(MouseEvent e) {

                }
            });
        }

        main.add(jPanel1);
        main.add(Box.createVerticalStrut(30));
        main.add(jPanel2);
        frame.setVisible(true);
        reload();
        time();

    }
    public static void reload(){
        choilai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int demo = JOptionPane.showConfirmDialog(new Frame(),"Bạn Có Chắc Sẽ Chơi Lại Không", "Thông Báo",JOptionPane.YES_NO_OPTION);

                if( demo == JOptionPane.YES_OPTION){
                    counter = 0;
                    jProgressBar.setValue(counter);
                    time();

                    Collections.shuffle(listImage);
                    int index = 0;
                    for (Component component : con2.getComponents()) {
                        if(component instanceof JButton){
                            JButton button = (JButton) component; // ép kiểu component về jbutton
                            button.setIcon(null);
                            button.setName(listImage.get(index).getName());

                            File imageFile = listImage.get(index);
                            String imagePath = imageFile.getPath();
                            ImageIcon imageIcon = new ImageIcon(imagePath);
                            Image resize = imageIcon.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
                            imageIcon = new ImageIcon(resize);
                            button.setIcon(imageIcon);
                            index++;
                            counter=0;
                        }
                    }
                }else {
                        JOptionPane.getRootFrame().dispose();
                }

            }
        });
    }
    private static void getList(){
        // lấy folder chứa ảnh
        File gallery = new File("imagee");
        // lấy ra danh sách chứa folder trên
        File[] imgarr = gallery.listFiles();
        //Nhân đôi mảng
        listImage = new ArrayList<>(Arrays.asList(imgarr));
        listImage.addAll(Arrays.asList(imgarr));
        //xáo trộn mảng
        Collections.shuffle(listImage);
    }
    public static void time(){
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jProgressBar.setMinimum(0);
                jProgressBar.setMaximum(60);
                if(jProgressBar.getValue() < jProgressBar.getMaximum()){
                    jProgressBar.setValue(counter);
                }
                System.out.println(jProgressBar.getValue());
                if(jProgressBar.getValue() == jProgressBar.getMaximum()){
                    ((Timer) e.getSource()).stop();
                    int op = JOptionPane.showConfirmDialog(new Frame(),"Bạn có muốn chơi lại không","Gà vl",JOptionPane.YES_NO_OPTION);
                }

                counter++;
            }
        });

        timer.start();

    }


}

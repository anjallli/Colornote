import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.net.URI; 
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


class TextEditor implements ActionListener {

    JFrame f;
    JMenuBar menuBar;
    JMenu file,
            edit,
            themes,
            help;
    JTextArea textArea;
    JScrollPane scroll;
    JMenuItem darkTheme,
            moonLightTheme,
            defaultTheme,
            save,
            open,
            close,
            cut,
            copy,
            paste,
            New,
            selectAll,
            about,
            aboutcolornote, 
            font,
            screenshot,
            takescreenshot;
    JPanel saveFileOptionWindow;
    JLabel fileLabel, dirLabel;
    JTextField fileName, dirName;


    TextEditor(){
        f = new JFrame("Color Note"); //setting the frame
        Image img = Toolkit.getDefaultToolkit().getImage("src\\com\\company\\logo.JPG"); //adding image
        f.setIconImage(img);
        menuBar = new JMenuBar();

        //menues
        file = new JMenu("File");
        edit = new JMenu("Edit");
        themes = new JMenu("Themes");
        screenshot = new JMenu("Screenshot");
        about = new JMenu("About");

        //adding menues to menubar
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(themes);
        menuBar.add(screenshot);
        menuBar.add(about);
        f.setJMenuBar(menuBar);

        //adding submenus to file
        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        New = new JMenuItem("New");
        close = new JMenuItem("Exit");
        file.add(open);
        file.add(New);
        file.add(save);
        file.add(close);

        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");    
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select all");
        font = new JMenuItem("Font");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(font);

        takescreenshot = new JMenuItem("Take Screenshot");
        screenshot.add(takescreenshot);

        aboutcolornote = new JMenuItem("About Colornote");
        about.add(aboutcolornote);

        //adding themes
        darkTheme = new JMenuItem("Dark Theme");
        moonLightTheme = new JMenuItem("Moonlight Theme");
        defaultTheme = new JMenuItem("Default Theme");
        themes.add(darkTheme);
        themes.add(moonLightTheme);
        themes.add(defaultTheme);


        //Textarea
        textArea = new JTextArea(80,88);
        f.add(textArea);

        //scrollpane
        scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(scroll);

        //adding event listeners for cut , copy & paste
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        font.addActionListener(this);
        open.addActionListener(this); //open the file
        save.addActionListener(this); //Save the file
        New.addActionListener(this); //Create the new document
        darkTheme.addActionListener(this); //dark theme
        moonLightTheme.addActionListener(this); //moonlight theme
        defaultTheme.addActionListener(this); // default theme
        close.addActionListener(this); //close the window
        about.addActionListener(this);
        aboutcolornote.addActionListener(this);
        screenshot.addActionListener(this);
        takescreenshot.addActionListener(this);

        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {}

            @Override
            public void windowClosing(WindowEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(f,"Do you want to exit?","Confirm Before Saving",JOptionPane.YES_NO_OPTION);

                if (confirmExit == JOptionPane.YES_OPTION)
                    f.dispose();
                else if (confirmExit == JOptionPane.NO_OPTION)
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {}

            @Override
            public void windowIconified(WindowEvent windowEvent) {}

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {}

            @Override
            public void windowActivated(WindowEvent windowEvent) {}

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {}
        });

        //Keyboard Listeners
        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_S && e.isControlDown())
                    saveTheFile(); //Saving the file
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        };
        textArea.addKeyListener(k);

        //Default Operations for frame
        f.setSize(1000,596);
        f.setResizable(true);
        f.setLocation(250,100);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Copy paste operations
        if (e.getSource()==cut)
            textArea.cut();
        if (e.getSource()==copy)
            textArea.copy();
        if (e.getSource()==paste)
            textArea.paste();
        if (e.getSource()==selectAll)
            textArea.selectAll();


    if (e.getSource()==font){

      String sizeOfFont = JOptionPane.showInputDialog(f,"Enter Font Size ",JOptionPane.OK_CANCEL_OPTION);
      String styleOfFont = JOptionPane.showInputDialog("Enter Font Style \npress 1  for  italic \n press2 for Plain \n press3 for Bold",JOptionPane.OK_CANCEL_OPTION);
      int convertStyleOfFont = Integer.parseInt(styleOfFont);

                if (convertStyleOfFont == 1){
                    int  convertSizeOfFont = Integer.parseInt(sizeOfFont);
                    Font font = new Font(Font.SANS_SERIF,Font.ITALIC,convertSizeOfFont);
                    textArea.setFont(font);
                }
                 if (convertStyleOfFont == 2){
                   
                    int  convertSizeOfFont = Integer.parseInt(sizeOfFont);
                    Font font = new Font(Font.SANS_SERIF,Font.PLAIN,convertSizeOfFont);
                    textArea.setFont(font);

                }
                 if (convertStyleOfFont == 3){   
                    int  convertSizeOfFont = Integer.parseInt(sizeOfFont);
                    Font font = new Font(Font.SANS_SERIF,Font.BOLD,convertSizeOfFont);
                    textArea.setFont(font);

                }
        }

        //Open the file
        if (e.getSource()==open){
            JFileChooser chooseFile = new JFileChooser();
            int i = chooseFile.showOpenDialog(f);
            if (i == JFileChooser.APPROVE_OPTION){
                File file = chooseFile.getSelectedFile(); //select the file
                String filePath = file.getPath(); //get the file path
                String fileNameToShow = file.getName(); //get the file name
                f.setTitle(fileNameToShow);

               try {
                   BufferedReader readFile = new BufferedReader(new FileReader(filePath));
                   String tempString1 = "";
                   String tempString2 = "";

                   while ((tempString1 = readFile.readLine()) != null)
                        tempString2 += tempString1 + "\n";

                   textArea.setText(tempString2);
                   readFile.close();
               }catch (Exception ae){
                   ae.printStackTrace();
               }
            }
        }

           //Save the file
        if (e.getSource()==save) {
            JFileChooser chooseFile = new JFileChooser("f: ");
            int i = chooseFile.showSaveDialog(f);
 
            if (i == JFileChooser.APPROVE_OPTION) {
 
                File file = chooseFile.getSelectedFile(); //select the file
                String filePath = file.getPath();
 
                   try {
                   FileWriter wr = new FileWriter(file, false); 
                   BufferedWriter w = new BufferedWriter(wr);               
                   w.write(textArea.getText());
                    w.flush();
                    w.close();

               }
               catch (Exception ae){
               JOptionPane.showMessageDialog(f, "File Successfully saved!");
                   
            }
        }
    }

        //New menu operations
        if (e.getSource()==New) textArea.setText("");


        //Exit from the window
        if (e.getSource()==close) System.exit(1);


        //themes area
        if (e.getSource()==darkTheme){
            textArea.setBackground(Color.DARK_GRAY);        //dark Theme
            textArea.setForeground(Color.WHITE);
        }

        if (e.getSource()==moonLightTheme){
            textArea.setBackground(new Color(107, 169, 255));
            textArea.setForeground(Color.black);
        }

        if (e.getSource() == defaultTheme){
            textArea.setBackground(new Color(255, 255, 255));
            textArea.setForeground(Color.black);
        }


          if (e.getSource()==takescreenshot) {
          try {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = "FullScreenshot." + format;
             
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));
             
            System.out.println("A full screenshot saved!");
            }   
            catch (AWTException | IOException ex) {
            System.err.println(ex);

        }
    }
    
        if (e.getSource()==aboutcolornote) {
         
        JOptionPane.showMessageDialog(f,"Made in jdk8\nDeveloped on :14/05/2022 \nPlatform:Ubuntu(Version 8.0)","Color Note",JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getActionCommand().equals("cut")){
            textArea.cut();
        }     

}


//Save the file
    public void saveTheFile(){
      JFileChooser chooseFile = new JFileChooser("f: ");
            int i = chooseFile.showSaveDialog(f);
 
            if (i == JFileChooser.APPROVE_OPTION) {
 
                File file = chooseFile.getSelectedFile(); //select the file
                String filePath = file.getPath();
 
                   try {

                   FileWriter wr = new FileWriter(file, false); 
                   BufferedWriter w = new BufferedWriter(wr);               
                   w.write(textArea.getText());
                    w.flush();
                    w.close();

               }
               catch (Exception ae){
               JOptionPane.showMessageDialog(f, "File Successfully saved!");
                   
            }
        }

    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
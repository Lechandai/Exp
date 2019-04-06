package lol;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
 
/* PasswordDemo.java requires no other files. */
 
public class PasswordDemo extends JPanel
                          implements ActionListener {
    protected static final boolean isPasswordCorrect = false;
	private static String OK = "ok";
    private static String HELP = "help";
    private JFrame controllingFrame; //needed for dialogs
    private JPasswordField passwordField;
    public PasswordDemo(JFrame f) {
        controllingFrame = f;
        passwordField = new JPasswordField(10);
        passwordField.setActionCommand(OK);
        passwordField.addActionListener(this);
 
        JLabel label = new JLabel("Введите пароль: ");
        label.setLabelFor(passwordField);
 
        JComponent buttonPane = createButtonPanel();
 
        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);
 
        add(textPane);
        add(buttonPane);
    }
 
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("Help");
 
        okButton.setActionCommand(OK);
        helpButton.setActionCommand(HELP);
        okButton.addActionListener(this);
        helpButton.addActionListener(this);
 
        p.add(okButton);
        p.add(helpButton);
 
        return p;
    }
 
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
 
        if (OK.equals(cmd)) { //Process the password.
            char[] input = passwordField.getPassword();
            if (isPasswordCorrect(input)) {
                JOptionPane.showMessageDialog(controllingFrame,
                    "Пароль правильный");
                Nalog.main1(null);
                controllingFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(controllingFrame,
                    "Пароль не верный",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            }
 
            //Zero out the possible password, for security.
            Arrays.fill(input, '0');
 
            passwordField.selectAll();
            resetFocus();
        } else { //The user has asked for help.
            JOptionPane.showMessageDialog(controllingFrame,
                "Пароль 'admin'");
        }
    }
    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = { 'a', 'd', 'm', 'i', 'n'};
 
        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }
 
        //Zero out the password.
        Arrays.fill(correctPassword,'0');
 
        return isCorrect;
    }
 
    //Must be called from the event dispatch thread.
    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("PasswordDemo");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
 
        //Create and set up the content pane.
        final PasswordDemo newContentPane = new PasswordDemo(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Make sure the focus goes to the right component
        //whenever the frame is initially given the focus.
        frame.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                newContentPane.resetFocus();
            }
        });
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
            }
        });
    }
}
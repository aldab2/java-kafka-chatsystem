package org.example;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JOptionPaneMultiInput implements ActionListener {
    JTextField xField ;//= new JTextField(5);
    JTextField yField ;//= new JTextField(5);

    String username;
    String chatroom;


    ArrayList<String> rooms;
    JOptionPaneMultiInput(ArrayList<String> rooms){
        this.rooms = rooms;
        this.rooms.forEach(room -> System.out.println(room));
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        JComboBox topicList = new JComboBox(this.rooms.toArray());
        topicList.setSelectedIndex(0);
        topicList.addActionListener(this);
        myPanel.add(new JLabel("username:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("chatroom:"));
        myPanel.add(topicList);
        myPanel.add(Box.createVerticalStrut(5)); // a spacer
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(("Rooms are:"));
        String str = "Rooms are:";
        rooms.forEach(room -> {
           stringBuilder.append(room);

        });

        myPanel.add(new JLabel(stringBuilder.toString()));


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter username and chatroom", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            this.username = xField.getText();
            this.chatroom = (String) topicList.getSelectedItem();
            System.out.println("x value: " + xField.getText());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Setting Chatroom!");
        JComboBox cb = (JComboBox)e.getSource();
        this.chatroom = (String)cb.getSelectedItem();
    }
}

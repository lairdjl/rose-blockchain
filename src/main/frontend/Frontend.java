package frontend;

import communication.Client;
import communication.ClientConnection;
import communication.Server;
import communication.ServerConnection;
import helpers.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.net.SocketException;

import static helpers.Helpers.log;

public class Frontend {
    public JFrame frame = new JFrame("Client");
    //    private JFrame messageFrame = new JFrame();

    public JPanel messagePanel = new JPanel();
//    private JPanel

    public JTextField connectionField = new JTextField(40);
    public JTextArea ipArea = new JTextArea(8, 60);

    public JTextField dataField = new JTextField(40);
    public JTextArea messageArea = new JTextArea(8, 60);

    private static Frontend instance;
    private static ServerConnection connection;
    private static Socket socket;

    public Frontend(ServerConnection connection, Socket socket) {
        this.connection = connection;
        this.socket = socket;

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield
             * by sending the contents of the text field to the
             * communication and displaying the response from the communication
             * in the text area.  If the response is "." we exit
             * the whole application, which closes all sockets,
             * streams and windows.
             */
            public void actionPerformed(ActionEvent e) {
                log("Action performed");
                JSONObject jsonObject = new JSONObject(socket.getInetAddress(), dataField.getText());
                connection.write(jsonObject.getJSONTransaction());
            }
        });

        // Layout GUI
        messageArea.setEditable(false);
//        frame.setLayout(new GridLayout(2,2));
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.getContentPane().add(dataField, "South");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public String getServerAddress() {
        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the blockchain.Blockchain client",
                JOptionPane.QUESTION_MESSAGE);
        return serverAddress;
    }


}

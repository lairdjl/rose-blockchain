package frontend;

import communication.Client;
import communication.ServerConnection;
import helpers.TransactionJSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import static helpers.Helpers.log;

public class Frontend {

    private static final int rows = 1;
    private static final int cols = 2;
    public JFrame frame = new JFrame("Client");

    public JTextField connectionField = new JTextField(40);
    public JTextArea ipArea = new JTextArea(8, 60);

    public JTextField dataField = new JTextField(40);
    public JTextArea messageArea = new JTextArea(8, 60);

    private static Frontend instance;
//    private static ArrayList<ServerConnection> serverList = new ArrayList<>();
    private static Socket socket;
    private static Client client;

    public Frontend(Client client, Socket socket) {

        this.client = client;
        this.socket = socket;

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield
             * by sending the contents of the text field to the
             * communication and displaying the response from the communication
             * in the text area.
             */
            public void actionPerformed(ActionEvent e) {
                log("Action performed");
                for (ServerConnection conn: Client.getServerList()){
                    TransactionJSONObject transactionJsonObject = new TransactionJSONObject(socket.getInetAddress(), conn.getInetAddress(), dataField.getText());
                    conn.write(transactionJsonObject.getJSONTransaction());
                }
            }
        });

        connectionField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = connectionField.getText().toString();
                client.addConnection(ip);
            }
        });

        // Layout GUI
        messageArea.setEditable(false);

        frame.setLayout(new GridLayout(rows,cols));
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.add(new JScrollPane(messageArea));
        messagePanel.add(dataField);
        frame.getContentPane().add(messagePanel);

        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.Y_AXIS));
        connectionPanel.add(new JScrollPane(ipArea));
        connectionPanel.add(connectionField);
        frame.add(connectionPanel);

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

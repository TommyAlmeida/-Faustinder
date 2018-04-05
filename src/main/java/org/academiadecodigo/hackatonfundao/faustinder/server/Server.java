package org.academiadecodigo.hackatonfundao.faustinder.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Map<String, ClientHandler> clients;
    private String clientName;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new HashMap<>();
    }

    public void start() throws IOException {
        int i = 0;
        ExecutorService service = Executors.newCachedThreadPool();

        while (true) {

            i++;
            Socket clientSocket = serverSocket.accept();
            clientName = "Guest" + i;
            ClientHandler handler = new ClientHandler(clientSocket);
            service.submit(handler);

            clients.put(clientName, handler);
            System.out.println("Connection!!!");
            System.out.println(clientName);
        }
    }

    private void broadcast(String message) {
        for( Map.Entry<String,ClientHandler> entry : clients.entrySet()){
             entry.getValue().writeMessage(message);
        }
    }
    class ClientHandler implements Runnable {

        private Socket connection;
        private BufferedReader fromClient;
        private PrintWriter toClient;


        public ClientHandler(Socket clientSocket) throws IOException {
            this.connection = clientSocket;
            this.toClient = new PrintWriter(connection.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                fromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while (true) {

                    String message = fromClient.readLine();

                    if (message == null) {
                        break;
                    }
                    if (message.isEmpty()) {
                        continue;
                    }
                    broadcast(message);
                }

            } catch (IOException e) {
                System.err.println("Error while processing the text");

            } finally {
                stop();
            }
        }

        private void stop() {
            try {
                connection.close();
            } catch (IOException e) {
                System.err.println("Error closing the connection!");
            }
        }

        public void writeMessage(String message) {
            toClient.println(message);
        }
    }



}
package org.academiadecodigo.hackatonfundao.faustinder.server;

import java.io.IOException;

public class ServerLauncher {

    public static void main(String[] args) {

        /*if(args.length != 1){
            System.out.println("Usage: java -jar ServerLauncher <PortNumber>");
            return;
        }*/

        int port = 9000;
        try{
            Server server = new Server(port);
            server.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

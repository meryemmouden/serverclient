package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chatwithserver extends Thread {
    private int clientnbr;
    private List<Communication> clientsconnectes = new ArrayList<Communication>();

    public static void main(String[] args) {
        new Chatwithserver().start();
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);

            System.out.println("Le serveur essaie de démarrer...");
            while (true) {
                Socket s = ss.accept();
                ++clientnbr;

                Communication Newcommunication = new Communication(s, clientnbr);
                clientsconnectes.add(Newcommunication);
                Newcommunication.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Communication extends Thread {
        private Socket s;
        private int clientnbbr;

        Communication(Socket s, int clientnbre) {
            this.s = s;
            this.clientnbbr = clientnbre;
        }

        @Override
        public void run() {
            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = s.getOutputStream();
                String IP = s.getRemoteSocketAddress().toString();
                System.out.println("Le client numéro " + clientnbbr + " et son IP " + IP);
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("Vous êtes le client " + clientnbbr);
                pw.println("Envoyer le message que vs voulez ... :D ");
                while (true) {
                    String userRequest = br.readLine();
                    if (userRequest.contains("=>")) {
                        String[] usermessage = userRequest.split("=>");
                        if (usermessage.length == 2) {
                            String msg = usermessage[1];
                            int numeroClient = Integer.parseInt(usermessage[0]);
                            Send(msg, s, numeroClient);
                        }
                    } else {
                        Send(userRequest, s, -1);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void Send(String userRequest, Socket socket, int nbre) throws IOException {
            for (Communication client : clientsconnectes) {
                if (client.s != socket) {
                    if (client.clientnbbr == nbre || nbre == -1) {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println(userRequest);
                    }

                }
            }
        }
    }
}
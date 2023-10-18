package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Playwithserver extends Thread {
    private int clientnbr;
    private int secretnbre;
    private boolean fin;
    private String winner;

    public static void main(String[] args) {
        new Playwithserver().start();
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            secretnbre = new Random().nextInt(100);
            System.out.println(secretnbre);
            System.out.println("Le serveur essaie de démarrer...");
            while (true) {
                Socket s = ss.accept();
                ++clientnbr;
                new Communication(s, clientnbr).start();
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
                pw.println("Devinez le nombre secret... :D");
                while (true) {
                    String userRequest = br.readLine();
                    boolean requestFormat = false;
                    int userNbre = 0;
                    try {
                        userNbre = Integer.parseInt(userRequest);
                        requestFormat = true;
                    } catch (NumberFormatException e) {
                        pw.println("Mon ami, donne-moi un nombre, pas une phrase.");
                    }
                    if (requestFormat) {
                        System.out.println("Le client " + clientnbr + " a envoyé le nombre " + userNbre);
                        if (!fin) {
                            if (userNbre > secretnbre)
                                pw.println("Votre nombre est supérieur au nombre secret.");
                            else if (userNbre < secretnbre)
                                pw.println("Votre nombre est inférieur au nombre secret.");
                            else {
                                pw.println("C'est très bien, frère.");
                                System.out.println("Le gagnant est le client numéro " + clientnbbr + " et son IP " + IP);
                                fin = true;
                            }
                        } else {
                            pw.println("On a terminé, frère... Et le gagnant est le client " + clientnbbr);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

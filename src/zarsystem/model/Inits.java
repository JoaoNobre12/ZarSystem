package zarsystem.model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Testa conexão com a internet e mostra mensagem de inicialização no console
 * Created by joaov on 23/11/2016.
 */
public class Inits extends Thread{
    /**
     * testa conexão com a internet;
     */
    private static boolean testInet(String site) {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress(site,80);
        try {
            sock.connect(addr,3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {sock.close();}
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void run(){

        System.out.println("        2015 - 2016           ");
        System.out.println("Grow Up! Softwares & WebSites");
        System.out.println("         ZarSystem            ");

        System.out.println();
        System.out.println(
                "Online: " +
                        (testInet("google.com") || testInet("facebook.com")));
    }
}

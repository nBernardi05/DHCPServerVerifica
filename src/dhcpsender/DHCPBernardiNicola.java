package dhcpsender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author bernardi.nicola
 */
public class DHCPBernardiNicola {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket=new DatagramSocket(52000);        // il server dovrebbe essere in ascolto sulla porta 67 e inviare i messaggi nella 68
        DatagramPacket dp;
        byte [] ricevi = new byte[1000];
        while (true) {
            dp = new DatagramPacket(ricevi, ricevi.length);
            socket.receive(dp);
            MsgBernardiNicola msg = new MsgBernardiNicola(ricevi);
            msg.stampa();
        }
    }
}

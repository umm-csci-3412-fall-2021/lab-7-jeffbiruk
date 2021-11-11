package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketManager {
    int finalID = 0;
    public boolean haveReceivedAllPackets(){
        return true;
    }
    public void receive(DatagramPacket packet){
        byte[] bytes = packet.getData();
        byte status = bytes[0];
        byte fileID = bytes[1];
        byte[] fileName = Arrays.copyOfRange(bytes,2,bytes.length - 1);

        if (status % 2 == 0){ // Header Packet

        } else { // Data packet
            if (status % 4 ==3) finalID = fileID;
        }
    }
}

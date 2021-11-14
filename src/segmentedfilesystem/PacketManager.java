package segmentedfilesystem;

import segmentedfilesystem.Packets.DataPacket;
import segmentedfilesystem.Packets.HeaderPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PacketManager {
    int finalID;
    boolean receivedAll;
    List<ReceivedFile> files;

    public PacketManager() {
        finalID = 0;
        receivedAll = false;
        files = new ArrayList<ReceivedFile>();
    }

    public boolean haveReceivedAllPackets() {
        return receivedAll;
    }

    public void receive(DatagramPacket packet) {
        byte[] bytes = packet.getData();
        byte status = bytes[0];

        if (status % 2 == 0) { // Header Packet
            HeaderPacket headerPacket = new HeaderPacket(bytes);
            files = files.stream().peek(file -> {
                if (file.fileID == headerPacket.getFileID()) {
                    file.setFileName(headerPacket.getFileName());
                }
            }).collect(Collectors.toList());
        } else { // Data packet
            DataPacket dataPacket = new DataPacket(bytes);

            if (fileExists(dataPacket.getFileID())) {
                files = files.stream().peek(file -> {
                    if (file.fileID == dataPacket.getFileID()) {
                        file.packets.put(dataPacket.getPacketNumber(), dataPacket.getData());
                    }
                }).collect(Collectors.toList());
            } else {
                ReceivedFile newFile = new ReceivedFile(dataPacket.getFileID());
                newFile.packets.put(dataPacket.getPacketNumber(), dataPacket.getData());
                files.add(newFile);
            }

            if (status % 4 == 3) {
                finalID = dataPacket.getFileID();
                receivedAll = true;
                // this needs modification. just because the final packet was received doesnt mean downloading should stop
                // needs to check that all three files have received all the packets that they need somehow
            }
        }
    }

    private boolean fileExists(byte fileID) {
        return files.stream().anyMatch(file -> file.fileID == fileID);
    }

    public void saveFiles(){
        for (ReceivedFile file: files){
            try (FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/" + file.fileName)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

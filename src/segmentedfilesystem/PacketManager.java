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
    List<ReceivedFile> files;

    public PacketManager() {
        files = new ArrayList<>();
    }

    public boolean haveReceivedAllPackets() {
        return files.stream().allMatch(file->file.complete);
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

            if (status % 4 == 3) { // final packet for a file
                int finalPacketNumber = dataPacket.getPacketNumber();
                files = files.stream().peek(file->{
                    if (file.fileID == dataPacket.getFileID()) file.finalPacketID = finalPacketNumber;
                }).collect(Collectors.toList());
            }

            if (fileExists(dataPacket.getFileID())) {
                files = files.stream().peek(file -> {
                    if (file.fileID == dataPacket.getFileID()) {
                        file.addPacket(dataPacket);
                    }
                }).collect(Collectors.toList());
            } else {
                ReceivedFile newFile = new ReceivedFile(dataPacket.getFileID());
                newFile.addPacket(dataPacket);
                files.add(newFile);
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

package segmentedfilesystem;

import segmentedfilesystem.Packets.DataPacket;
import segmentedfilesystem.Packets.HeaderPacket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ReceivedFile {
    public byte fileID;
    public int finalPacketID;
    public String fileName;
    public SortedMap<Integer, byte[]> packets; // packet # -> data
    public boolean complete;
    public boolean receivedFinalPacket;

    public ReceivedFile(byte fileID) {
        this.fileID = fileID;
        this.packets = new TreeMap<>();
        this.complete = false;
        this.finalPacketID = -1;
    }

    public byte[] getBytes() {
        byte[] result = new byte[getFileSizeInBytes()];
        Iterator<byte[]> iterator = packets.values().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            for (byte a : iterator.next()) {
                result[index] = a;
                index++;
            }
        }
        return result;
    }

    public int getFileSizeInBytes() {
        int size = 0;
        for (byte[] a : packets.values()) {
            size += a.length;
        }
        return size;
    }

    public void addPacket(DataPacket packet) {
        packets.put(packet.getPacketNumber(), packet.getData());
        boolean complete = true;

        if (!receivedFinalPacket) complete = false;

        for (int i = 0; i < finalPacketID; i++) {
            if (!this.packets.containsKey(i)) {
                complete = false;
                break;
            }

        }
        if (complete) {
            System.out.println("Finished downloading " + fileName + ".");
        }
        this.complete = complete;
    }

    public void addPacket(HeaderPacket packet) {
        fileID = packet.getFileID();
        fileName = packet.getFileName();
    }

    public byte getFileID() {
        return fileID;
    }

    public void setFileID(byte fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SortedMap<Integer, byte[]> getPackets() {
        return packets;
    }

    public void setPackets(SortedMap<Integer, byte[]> packets) {
        this.packets = packets;
    }
}

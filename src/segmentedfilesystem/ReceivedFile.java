package segmentedfilesystem;

import java.util.Iterator;
import java.util.SortedMap;

public class ReceivedFile {
    public byte fileID;
    public String fileName;
    public SortedMap<Integer, byte[]> packets;

    public ReceivedFile(byte fileID) {
        this.fileID = fileID;
    }

    public byte[] getBytes(){
        byte[] result = new byte[getFileSizeInBytes()];
        Iterator<byte[]> iterator = packets.values().iterator();
        int index = 0;
        while (iterator.hasNext()){
             for (byte a: iterator.next()){
                 result[index] = a;
                 index++;
             }
        }
        return result;
    }

    public int getFileSizeInBytes(){
        int size = 0;
        for (byte[] a: packets.values()){
            size += a.length;
        }
        return size;
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

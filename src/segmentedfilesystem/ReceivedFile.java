package segmentedfilesystem;

import java.util.SortedMap;

public class ReceivedFile {
    public byte fileID;
    public String fileName;
    public SortedMap<Integer, byte[]> packets;

    public ReceivedFile(byte fileID) {
        this.fileID = fileID;
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

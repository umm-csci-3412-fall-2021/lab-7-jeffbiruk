package segmentedfilesystem.Packets;

import segmentedfilesystem.Packets.Packet;

import java.util.Arrays;

public class HeaderPacket extends Packet {
    byte status;
    String fileName;
    int packetSize;
    public HeaderPacket(byte[] bytes, int packetSize) {
        super(bytes);
        this.packetSize = packetSize;
        this.status = bytes[0];
        this.fileID = bytes[1];
        this.fileName = new String(Arrays.copyOfRange(bytes,2, packetSize));
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

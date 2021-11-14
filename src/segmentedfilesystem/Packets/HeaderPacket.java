package segmentedfilesystem.Packets;

import segmentedfilesystem.Packets.Packet;

import java.util.Arrays;

public class HeaderPacket extends Packet {
    byte status;
    String fileName;
    public HeaderPacket(byte[] bytes) {
        super(bytes);
        this.status = bytes[0];
        this.fileID = bytes[1];
        this.fileName = new String(Arrays.copyOfRange(bytes,2,bytes.length - 1));
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

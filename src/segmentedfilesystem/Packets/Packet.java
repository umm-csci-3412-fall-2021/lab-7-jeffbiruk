package segmentedfilesystem.Packets;

public abstract class Packet {
    byte fileID;
    byte[] bytes;
    public Packet(byte[] bytes){
        this.bytes = bytes;
    }
    public byte getFileID(){
        return fileID;
    }
}

import java.io.*;
import java.util.zip.*;

public class FixPlayer {
    public static void main(String[] args) throws Exception {
        String saveDir = "C:/Users/Yuanqiubai/Desktop/☆冒险者传说V2.0【制作中】/☆冒险者传说V1.91_测试调试/.minecraft/saves/1.905七周年后续更新/players";
        File dir = new File(saveDir);
        for (File f : dir.listFiles()) {
            if (!f.getName().endsWith(".dat")) continue;
            System.out.println("Processing: " + f.getName());
            
            // Read gzipped NBT
            DataInputStream in = new DataInputStream(new GZIPInputStream(new FileInputStream(f)));
            byte[] data = readAll(in);
            in.close();
            
            // Search for "ActiveEffects" tag and zero it out
            byte[] search = "ActiveEffects".getBytes("UTF-8");
            int idx = indexOf(data, search);
            if (idx < 0) {
                System.out.println("  No ActiveEffects found");
                continue;
            }
            
            // The tag before "ActiveEffects" should be TAG_List (9)
            // We need to replace the list with an empty list
            // Just change the tag type to TAG_Byte (1) with value 0, keeping length same
            // Actually simplest: change list count to 0
            // TAG_List: [type=9][name_len=2][name][subtag=1][count=4]
            // After the name, the subtag byte and count int — set count to 0
            
            int nameStart = idx - 2; // name length short
            int tagTypePos = nameStart - 1;
            byte tagType = data[tagTypePos];
            System.out.println("  Tag type before name: " + tagType + " (9=List)");
            
            // Position after "ActiveEffects" string
            int afterName = idx + search.length;
            // The list payload: subtag_type(1) + count(4)
            // Set count to 0
            int countPos = afterName + 1; // skip subtag type
            int oldCount = (data[countPos] << 24) | ((data[countPos+1] & 0xFF) << 16) | ((data[countPos+2] & 0xFF) << 8) | (data[countPos+3] & 0xFF);
            System.out.println("  Old effect count: " + oldCount);
            
            data[countPos] = 0;
            data[countPos+1] = 0;
            data[countPos+2] = 0;
            data[countPos+3] = 0;
            
            // Write back
            DataOutputStream out = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(f)));
            out.write(data);
            out.close();
            System.out.println("  Fixed! Effects cleared.");
        }
    }
    
    static byte[] readAll(DataInputStream in) throws IOException {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
    
    static int indexOf(byte[] data, byte[] pattern) {
        outer: for (int i = 0; i < data.length - pattern.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i+j] != pattern[j]) continue outer;
            }
            return i;
        }
        return -1;
    }
}

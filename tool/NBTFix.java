import java.io.*;
import java.util.zip.*;
import net.minecraft.nbt.*;

public class NBTFix {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java NBTFix <level.dat path>");
            return;
        }
        File file = new File(args[0]);
        
        // Read
        NBTTagCompound root;
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            root = (NBTTagCompound) CompressedStreamTools.readCompressed(in);
        }
        
        NBTTagCompound data = root.getCompoundTag("Data");
        NBTTagCompound gameRules = data.getCompoundTag("GameRules");
        
        System.out.println("keepInventory was: " + gameRules.getString("keepInventory"));
        
        // Enable keepInventory
        gameRules.setString("keepInventory", "true");
        data.setCompoundTag("GameRules", gameRules);
        root.setCompoundTag("Data", data);
        
        // Write
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            CompressedStreamTools.writeCompressed(root, out);
        }
        
        System.out.println("\n[OK] keepInventory = true");
    }
}

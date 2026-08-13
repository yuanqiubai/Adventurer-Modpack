import java.io.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.CompressedStreamTools;

public class ReadAmmo {
    public static void main(String[] args) throws Exception {
        File f = new File("F:/ATcraft-Addon-164/tool/test_engine.dat");
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        NBTTagCompound root = CompressedStreamTools.func_1136_a(in);
        NBTTagCompound r = root.getCompoundTag("root");
        NBTTagList ammo = r.getTagList("AMMO");
        System.out.println("Found " + ammo.tagCount() + " ammo items:\n");
        for (int i = 0; i < ammo.tagCount(); i++) {
            NBTTagCompound a = (NBTTagCompound) ammo.tagAt(i);
            System.out.println("  " + (i+1) + ". " + a.getString("item_internalName"));
        }
    }
}

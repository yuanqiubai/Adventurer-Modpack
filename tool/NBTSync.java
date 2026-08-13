import java.io.*;
import java.util.*;
import net.minecraft.nbt.*;

public class NBTSync {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java NBTSync <ref_level.dat> <test_level.dat>");
            return;
        }

        NBTTagCompound ref = read(args[0]);
        NBTTagCompound test = read(args[1]);

        NBTTagCompound refGR = ref.getCompoundTag("Data").getCompoundTag("GameRules");
        NBTTagCompound testData = test.getCompoundTag("Data");
        NBTTagCompound testGR = testData.getCompoundTag("GameRules");

        // Sync these rules from reference to test
        String[] syncKeys = {
            "commandBlockOutput", "doTileDrops",
            "animalbikesFlying", "animalbikesGhastNetherOnly",
            "animalbikesTerrainDamage", "mpmAllowEntityModels"
        };

        for (String key : syncKeys) {
            if (refGR.hasKey(key)) {
                String refVal = refGR.getString(key);
                String oldVal = testGR.hasKey(key) ? testGR.getString(key) : "(none)";
                testGR.setString(key, refVal);
                System.out.println(key + ": " + oldVal + " -> " + refVal);
            }
        }

        testData.setCompoundTag("GameRules", testGR);
        test.setCompoundTag("Data", testData);

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(args[1]))) {
            CompressedStreamTools.writeCompressed(test, out);
        }
        System.out.println("\n[DONE] Synced " + syncKeys.length + " rules");
    }

    static NBTTagCompound read(String path) throws Exception {
        try (DataInputStream in = new DataInputStream(new FileInputStream(path))) {
            return (NBTTagCompound) CompressedStreamTools.readCompressed(in);
        }
    }
}

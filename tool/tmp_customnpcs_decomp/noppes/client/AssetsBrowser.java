/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  bjt
 *  org.apache.commons.lang3.StringUtils
 */
package noppes.client;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import noppes.CustomNpcs;
import org.apache.commons.lang3.StringUtils;

public class AssetsBrowser {
    private static final ArrayList<File> locations = new ArrayList();
    public boolean isRoot;
    public HashSet<String> folders = new HashSet();
    public HashSet<String> files = new HashSet();
    private int depth;
    private String folder;
    private final String[] extensions;

    public AssetsBrowser(String folder, String[] extensions) {
        this.extensions = extensions;
        this.setFolder(folder);
    }

    public AssetsBrowser(String[] extensions) {
        this.extensions = extensions;
    }

    public static boolean isAssetFile(File file) {
        try {
            if (!file.isDirectory() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip"))) {
                ZipFile var6 = new ZipFile(file);
                Enumeration<? extends ZipEntry> var7 = var6.entries();
                while (var7.hasMoreElements()) {
                    ZipEntry var8 = var7.nextElement();
                    String var9 = var8.getName();
                    if (!var9.startsWith("assets/")) continue;
                    var6.close();
                    return true;
                }
                var6.close();
            } else if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    if (!f.isDirectory() || !f.getName().equals("assets")) continue;
                    return true;
                }
            }
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
        return false;
    }

    public static String getRoot(String asset) {
        String location;
        String mod = "minecraft";
        int index = asset.indexOf(":");
        if (index > 0) {
            mod = asset.substring(0, index);
            asset = asset.substring(index + 1);
        }
        if (asset.startsWith("/")) {
            asset = asset.substring(1);
        }
        if ((index = (location = "/" + mod + "/" + asset).lastIndexOf("/")) > 0) {
            location = location.substring(0, index);
        }
        return location;
    }

    public void setFolder(String folder) {
        if (!folder.endsWith("/")) {
            folder = folder + "/";
        }
        this.isRoot = folder.length() <= 1;
        this.folder = "/assets" + folder;
        this.depth = StringUtils.countMatches((CharSequence)this.folder, (CharSequence)"/");
        this.getFiles();
    }

    private void getFiles() {
        File file2;
        this.folders.clear();
        this.files.clear();
        for (File file2 : locations) {
            this.progressFile(file2);
        }
        bjt repos1 = atv.w().L();
        file2 = new File(repos1.e(), repos1.d());
        if (file2.exists()) {
            this.progressFile(file2);
        }
        this.checkFolder(new File(CustomNpcs.Dir, "assets"), CustomNpcs.Dir.getAbsolutePath().length());
    }

    private void progressFile(File file) {
        try {
            if (!file.isDirectory() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip"))) {
                ZipFile e1 = new ZipFile(file);
                Enumeration<? extends ZipEntry> entries = e1.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipentry = entries.nextElement();
                    String entryName = zipentry.getName();
                    this.checkFile(entryName);
                }
                e1.close();
            } else if (file.isDirectory()) {
                int e = file.getAbsolutePath().length();
                this.checkFolder(file, e);
            }
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    private void checkFolder(File file, int length) {
        File[] files = file.listFiles();
        if (files != null) {
            File[] arr$ = files;
            int len$ = files.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                File f = arr$[i$];
                String name = f.getAbsolutePath().substring(length);
                if (!(name = name.replace("\\", "/")).startsWith("/")) {
                    name = "/" + name;
                }
                if (f.isDirectory() && (this.folder.startsWith(name) || name.startsWith(this.folder))) {
                    this.checkFile(name + "/");
                    this.checkFolder(f, length);
                    continue;
                }
                this.checkFile(name);
            }
        }
    }

    private void checkFile(String name) {
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        if (name.startsWith(this.folder)) {
            String[] split = name.split("/");
            int count = split.length;
            if (count == this.depth + 1) {
                if (this.validExtension(name)) {
                    this.files.add(split[this.depth]);
                }
            } else if (this.depth + 1 < count) {
                this.folders.add(split[this.depth]);
            }
        }
    }

    private boolean validExtension(String entryName) {
        int index = entryName.indexOf(".");
        if (index < 0) {
            return false;
        }
        String extension = entryName.substring(index + 1);
        for (String ex : this.extensions) {
            if (!ex.equalsIgnoreCase(extension)) continue;
            return true;
        }
        return false;
    }

    public String getAsset(String asset) {
        String[] split = this.folder.split("/");
        if (split.length < 3) {
            return null;
        }
        String texture = split[2] + ":";
        texture = texture + this.folder.substring(texture.length() + 8) + asset;
        return texture;
    }

    static {
        URLClassLoader loader = (URLClassLoader)atv.class.getClassLoader();
        for (URL url : loader.getURLs()) {
            try {
                File e = new File(url.toURI());
                if (!AssetsBrowser.isAssetFile(e)) continue;
                locations.add(e);
            }
            catch (URISyntaxException var6) {
                var6.printStackTrace();
            }
        }
    }
}


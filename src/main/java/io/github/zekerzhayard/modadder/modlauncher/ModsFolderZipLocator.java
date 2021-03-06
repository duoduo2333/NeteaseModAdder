package io.github.zekerzhayard.modadder.modlauncher;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModsFolderLocator;
import org.apache.commons.io.FileUtils;

public class ModsFolderZipLocator extends ModsFolderLocator {
    private Path modFolder = FMLPaths.MODSDIR.get();

    @Override
    public List<ModFile> scanMods() {
        List<File> modsFile = Lists.newArrayList(FileUtils.listFiles(this.modFolder.toFile(), new String[] { "zip" }, false));
        List<Path> excluded = ModAdder.candidatesModDirTransformer(FMLPaths.GAMEDIR.get());
        List<ModFile> modFiles = Lists.newArrayList();
        for (File modFile : modsFile) {
            Path p = modFile.toPath();
            if (excluded.contains(p)) {
                continue;
            }
            ModFile file = new ModFile(p, this);
            modFiles.add(file);
            this.modJars.put(file, this.createFileSystem(file));
        }
        return modFiles;
    }
}

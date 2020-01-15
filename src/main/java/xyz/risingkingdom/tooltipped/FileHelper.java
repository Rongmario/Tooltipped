package xyz.risingkingdom.tooltipped;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

public class FileHelper {

    public static boolean writeRegistriesToFile(String name) throws IOException {
        String fileName = name.concat(".txt");
        File file = createFile(fileName);
        FileWriter writer = new FileWriter(fileName);
        Formatter formatter = new Formatter();
        if (name.equals("biome")) {
            String formatted =
                    formatter.format("%-45s %-60s %-15s %-15s %-20s %-20s %-18s %-20s %-20s %-8s %-10s %-15s %-20s\n",
                    "*Names*", "*Unlocalized Name*", "*Category*", "*Sky Colour*", "*Precipitation Type*", "*Max Spawn Limit*",
                    "*Temperature*", "*Temperature Group*", "*Foliage Colour*", "*Depth*", "*Rainfall*",
                    "*Water Colour*", "*Water Fog Colour*").toString();
            writer.write(formatted);
            // System.out.println(formatted);
            for (Biome biome : Registry.BIOME) {
                formatter = new Formatter();
                formatted = formatter.
                        format("%-45s %-60s %-15s %-15s %-20s %-20s %-18s %-20s %-20s %-8s %-10s %-15s %-20s\n",
                        biome.getName().asFormattedString(), biome.getTranslationKey(), biome.getCategory().getName(),
                        biome.getSkyColor(), biome.getPrecipitation().getName(), biome.getMaxSpawnLimit(),
                        biome.getTemperature(), biome.getTemperatureGroup().getName(),
                        biome.getFoliageColor(), biome.getDepth(), biome.getRainfall(), biome.getWaterColor(),
                        biome.getWaterFogColor()).toString();
                writer.write(formatted);
                // System.out.println(formatted);
                writer.flush();
            }
            writer.close();
            return true;
        }
        else {
            return false;
        }
    }

    private static File createFile(String name) {
        File newFile = new File(name);
        return newFile;
    }

}

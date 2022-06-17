package net.timenation.lobbysystem.manager;

import lombok.Getter;
import lombok.SneakyThrows;
import net.timenation.lobbysystem.utils.Patch;
import net.timenation.timespigotapi.TimeSpigotAPI;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PatchManager {

    private final List<Patch> patches;

    public PatchManager() {
        this.patches = new ArrayList<>();
        initPatches();
    }

    @SneakyThrows
    public void initPatches() {
        ResultSet resultSet = TimeSpigotAPI.getInstance().getMySQL().getDatabaseResult("SELECT * FROM patchNotes");

        while (resultSet.next()) {
            patches.add(new Patch(resultSet.getString("patchTitle"), resultSet.getString("patchRelease"), resultSet.getString("patchDescription"), resultSet.getString("patchLink"), resultSet.getInt("patchId")));
        }
    }

    public void updatePatches() {
        patches.clear();
        initPatches();
    }
}

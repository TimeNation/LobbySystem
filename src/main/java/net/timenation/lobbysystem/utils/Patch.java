package net.timenation.lobbysystem.utils;

import lombok.Getter;

@Getter
public class Patch {

    private final String patchTitle;
    private final String patchRelease;
    private final String patchDescription;
    private final String patchLink;
    private final int patchId;

    public Patch(String patchTitle, String patchRelease, String patchDescription, String patchLink, int patchId) {
        this.patchTitle = patchTitle;
        this.patchRelease = patchRelease;
        this.patchDescription = patchDescription;
        this.patchLink = patchLink;
        this.patchId = patchId;
    }
}

package uk.avs.util;

import java.io.IOException;

public interface OnApproved {
    public void passed() throws IOException, InterruptedException;
}

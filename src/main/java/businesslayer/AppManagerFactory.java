package businesslayer;

import java.io.IOException;

public final class AppManagerFactory {
    private static AppManager manager;

    //create singleton manager
    public static AppManager GetManager() throws IOException {
        if (manager == null) {
            manager = new AppManagerImpl();
        }
        return manager;
    }
}

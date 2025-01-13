package pl.allblue.abdatanative;

import pl.allblue.abnative.ActionsSet;
import pl.allblue.abnative.NativeApp;

public class NativeDataStore {

    private NativeApp nativeApp = null;
    private ActionsSet nativeActions = null;

    public NativeDataStore(NativeApp nativeApp) {
        this.nativeApp = nativeApp;
    }

    public NativeApp getNativeApp() {
        return this.nativeApp;
    }

    public NativeTable getTable(String name) {
        return new NativeTable(this, name);
    }


}

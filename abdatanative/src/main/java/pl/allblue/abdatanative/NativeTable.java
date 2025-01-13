package pl.allblue.abdatanative;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.abnative.OnWebResultCallback;

public class NativeTable {

    private NativeDataStore dataStore = null;
    private String name = null;

    public NativeTable(NativeDataStore dataStore, String tableName) {
        this.name = tableName;
        this.dataStore = dataStore;
    }

    public void delete(JSONArray where, Integer transactionId,
            OnExecuteCallback callback) throws ABDataNativeException {
        JSONObject actionArgs = new JSONObject();
        try {
            actionArgs.put("transactionId", transactionId == null ?
                    JSONObject.NULL : transactionId);
            actionArgs.put("tableName", this.name);
            actionArgs.put("where", where);
        } catch (JSONException e) {
            throw new ABDataNativeException(e.getMessage(), e);
        }

        Log.d("Table", "Calling delete...");
        this.dataStore.getNativeApp().callWeb("ABData",
                "Table_Delete", actionArgs, new OnWebResultCallback() {
            @Override
            public void onResult(JSONObject result) throws JSONException {
                callback.onExecute(null);
            }

            @Override
            public void onError(String error) {
                Log.d("ABData Error -> Delete", error);
                try {
                    callback.onExecute(error);
                } catch (JSONException e) {
                    throw new AssertionError(e);
                }
            }
        });
    }

    public void select(JSONObject args, Integer transactionId,
            OnSelectCallback callback) throws ABDataNativeException {
        JSONObject actionArgs = new JSONObject();
        try {
            actionArgs.put("transactionId", transactionId == null ?
                    JSONObject.NULL : transactionId);
            actionArgs.put("tableName", this.name);
            actionArgs.put("args", args);
        } catch (JSONException e) {
            throw new ABDataNativeException(e.getMessage(), e);
        }

        Log.d("Table", "Calling select...");
        this.dataStore.getNativeApp().callWeb("ABData", "Table_Select",
                actionArgs, new OnWebResultCallback() {
            @Override
            public void onResult(JSONObject result) throws JSONException {
                callback.onSelect(result.getJSONArray("rows"), null);
            }

            @Override
            public void onError(String error) {
                Log.d("ABData Error -> Select", error);
                try {
                    callback.onSelect(null, error);
                } catch (JSONException e) {
                    throw new AssertionError(e);
                }
            }
        });
    }

    public void update(JSONArray rows, Integer transactionId,
            OnExecuteCallback callback) throws ABDataNativeException {
        Log.d("Test", this.name + ": " + transactionId);

        JSONObject actionArgs = new JSONObject();
        try {
            actionArgs.put("transactionId", transactionId == null ?
                    JSONObject.NULL : transactionId);
            actionArgs.put("tableName", this.name);
            actionArgs.put("rows", rows);
        } catch (JSONException e) {
            throw new ABDataNativeException(e.getMessage(), e);
        }

        Log.d("Table", "Calling update...");
        this.dataStore.getNativeApp().callWeb("ABData",
                "Table_Update", actionArgs, new OnWebResultCallback() {
            @Override
            public void onResult(JSONObject result) throws JSONException {
                callback.onExecute(null);
            }

            @Override
            public void onError(String error) {
                Log.d("ABData Error -> Update", error);
                try {
                    callback.onExecute(error);
                } catch (JSONException e) {
                    throw new AssertionError(e);
                }
            }
        });
    }


    public interface OnSelectCallback {
        void onSelect(JSONArray rows, String error) throws JSONException;
    }

    public interface OnExecuteCallback {
        void onExecute(String error) throws JSONException;
    }

}

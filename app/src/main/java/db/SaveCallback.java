package db;

public interface SaveCallback {
    void onSuccess();

    void onFailure(String message);
}

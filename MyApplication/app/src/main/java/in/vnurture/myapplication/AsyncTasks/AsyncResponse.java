package in.vnurture.myapplication.AsyncTasks;


public interface AsyncResponse {
    void onCallback(String response);
    void onFailure(String message);
}

package hu.ait.android.multiactivitydemo.data;

public class UserDataManager {

    private static UserDataManager instance = null;

    public static UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }

        return instance;
    }

    private User user;

    private UserDataManager() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
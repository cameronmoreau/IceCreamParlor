package teamoortcloud.engine;

/**
 * Created by Cameron on 11/29/15.
 */
public class ShopLog {

    String log;

    public ShopLog() {
        log = "";
    }

    public void addLog(String s) {
        log = (s + "\n" + log);
    }

    public String getMessages() { return log; }
}

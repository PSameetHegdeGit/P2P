import java.io.Serializable;

public class Tuple<X, Y> implements Serializable {
    public final X host;
    public final Y port;
    public Tuple(X x, Y y) {
        this.host = x;
        this.port = y;
    }
}

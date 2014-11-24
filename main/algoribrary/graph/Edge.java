package algoribrary.graph;

/**
 * Created by vadim on 21/11/14.
 */
public interface Edge {
    public int id();

    public int begin();

    public int end();

    public int weight();

    public int capacity();

    public int flow();

    public int reversed();

}

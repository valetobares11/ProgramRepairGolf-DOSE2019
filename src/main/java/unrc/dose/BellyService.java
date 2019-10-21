package unrc.dose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** This is a Service to store a bunch of Bellies together. */
public final class BellyService {

    /** Bellies store. */
    private static Map<String, Belly> bellies = new HashMap<String, Belly>();

    /** Atomic counter. */
    private static final AtomicInteger COUNT = new AtomicInteger(0);

    /** Returns a particular belly.
     * @param id String
     * @return Belly
    */
    public Belly findById(final String id) {
        return (Belly) bellies.get(id);
    }

    /** Adds a belly to store.
     * @param name String
     * @return Belly
    */
    public Belly add(final String name) {
        int currentId = COUNT.incrementAndGet();
        Belly belly = new Belly(currentId, name);
        bellies.put(String.valueOf(currentId), belly);
        return belly;
    }

    /** Updates a belly.
     * @param id String
     * @param cukes int
     * @return Belly
    */
    public Belly update(final String id, final int cukes) {
        Belly belly = (Belly) bellies.get(id);
        belly.eat(cukes);
        bellies.put(id, belly);

        return belly;
    }

    /** Deletes Belly.
     * @param id String
    */
    public void delete(final String id) {
        bellies.remove(id);
    }

    /** Returns a list of bellies.
     * @return List of bellies
    */
    public List findAll() {
        return new ArrayList<>(bellies.values());
    }
}

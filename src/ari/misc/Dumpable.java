package ari.misc;

/**
 * Created by Michael on 13/10/2015.
 *
 */
public interface Dumpable {
    String DUMP_TAB = "   ";

    String dump(int level);
}

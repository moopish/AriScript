package puel.misc;

/**
 * Created by Michael on 13/10/2015.
 *
 */
public interface Dumpable {
    /**
     * The tab, in spaces
     */
    String DUMP_TAB = "   ";

    /**
     * Makes a dump with a tab level
     * @param level the tab level
     * @return the dump
     */
    String dump(int level);

    /**
     * Dumps all sub parts at level 0
     * @return the dump of the sub parts
     */
    String subdump();
}

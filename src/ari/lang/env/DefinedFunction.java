package ari.lang.env;

import ari.misc.Dumpable;

/**
 * Created by Michael on 14/10/2015.
 *
 */
public class DefinedFunction implements Dumpable {

    private final String name;
    private final String namespace;
    private final DefinedType return_type;
    private final String[] param_names;
    private final DefinedType[] param_types;

    public DefinedFunction(String name, String namespace, DefinedType rtype, String[] pnames, DefinedType[] ptypes) {
        this.name = name;
        this.namespace = namespace;
        this.return_type = rtype;
        this.param_names = pnames;
        this.param_types = ptypes;
    }

    @Override
    public String dump(int level) {
        return null;
    }
}

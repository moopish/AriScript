package ari.lang.env;

import ari.misc.Dumpable;

/**
 * Created by Michael on 12/10/2015.
 *
 */
public final class Environment implements Dumpable {

    private final Namespace namespace;

    public Environment() {
        namespace = new Namespace(Namespace.EMPTY_NAMESPACE, Namespace.EMPTY_NAMESPACE);

        namespace.addType("int", Namespace.EMPTY_NAMESPACE, new DataType("int", Namespace.EMPTY_NAMESPACE, new String[0], new DataType[0]));
        namespace.addType("bool", Namespace.EMPTY_NAMESPACE, new DataType("bool", Namespace.EMPTY_NAMESPACE, new String[0], new DataType[0]));
    }

    public void namespace(String current_namespace, String new_namespace) {
        namespace.newSubspace(new_namespace, current_namespace);
    }

    public void defineType(String name, String namespace, String[] attr_names, String[] attr_types) {
        DataType[] attr_def = new DataType[attr_names.length];

        for (int i=0; i<attr_def.length; ++i) {
            attr_def[i] = getType(attr_types[i]);
        }

        this.namespace.addType(name, namespace, new DataType(name, namespace, attr_names, attr_def));
    }

    public DataType getType(String type_path) {
        return (namespace.getType(type_path));
    }

    public String dump(int level) {
        return (namespace.dump(level));
    }

    public String subdump() { return (namespace.subdump()); }

    @Override
    public String toString() {
        return (subdump());
    }
}

package ari.lang.env;

import ari.misc.Dumpable;
import ari.misc.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 12/10/2015.
 *
 */
public class Environment implements Dumpable {

    private final Map<String, Namespace> namespaces;

    public Environment() {
        namespaces = new HashMap<String, Namespace>();
        namespaces.put(Namespace.EMPTY_NAMESPACE, new Namespace(Namespace.EMPTY_NAMESPACE));

        namespaces.get(Namespace.EMPTY_NAMESPACE).addType("int", new DefinedType("int", "", new String[0], new DefinedType[0]));
        namespaces.get(Namespace.EMPTY_NAMESPACE).addType("bool", new DefinedType("bool", "", new String[0], new DefinedType[0]));
    }

    public void namespace(String current_namespace, String new_namespace) {
        if (!Util.isAlpha(new_namespace)) {
            throw new Namespace.InvalidNamespaceIDException(new_namespace);
        }

        if (!current_namespace.equals(Namespace.EMPTY_NAMESPACE)) {
            if (!Namespace.validNamespaceChain(current_namespace)) {
                throw new Namespace.InvalidNamespaceIDException(current_namespace);
            }

            String[] spaces = current_namespace.split("[.]");
            Namespace curr = namespaces.get(spaces[0]);

            for (int i=1; i<spaces.length; ++i) {
                curr = curr.getSubspace(spaces[i]);
            }
            
            curr.newSubspace(new_namespace);
        } else {
            if (!namespaces.containsKey(new_namespace))
                namespaces.put(new_namespace, new Namespace(new_namespace));
        }
    }

    public void defineType(String name, String namespace, String[] attr_names, String[] attr_types) {
        Namespace curr;

        if (!Util.isAlpha(name)) {
            throw new DefinedType.InvalidTypeIDException(name);
        }

        if (!namespace.equals(Namespace.EMPTY_NAMESPACE)) {
            String[] spaces = namespace.split("[.]");
            curr = namespaces.get(spaces[0]);

            for (int i = 1; i < spaces.length; ++i) {
                curr = curr.getSubspace(spaces[i]);
            }
        } else {
            curr = namespaces.get(Namespace.EMPTY_NAMESPACE);
        }

        if (curr.hasType(name)) {
            // TODO throw error
        }

        DefinedType[] attr_def = new DefinedType[attr_names.length];

        for (int i=0; i<attr_def.length; ++i) {
            attr_def[i] = getType(attr_types[i]);
        }

        curr.addType(name, new DefinedType(name, namespace, attr_names, attr_def));
    }

    public DefinedType getType(String type_path) {
        String[] spaces = type_path.split("[.]");

        if (spaces.length == 1) {
            return (namespaces.get(Namespace.EMPTY_NAMESPACE).getType(spaces[0]));
        } else {
            Namespace curr = namespaces.get(spaces[0]);

            for (int i = 1; i < spaces.length - 1; ++i) {
                curr = curr.getSubspace(spaces[i]);
            }
            return (curr.getType(spaces[spaces.length - 1]));
        }
    }

    public String dump(int level) {
        String dump = "";

        for (Namespace n : namespaces.values()) {
            dump += "\n" + n.dump(level) + "\n";
        }

        return (dump);
    }

    @Override
    public String toString() {
        return (dump(0));
    }
}

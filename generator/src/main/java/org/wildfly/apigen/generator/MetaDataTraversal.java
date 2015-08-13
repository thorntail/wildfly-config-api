package org.wildfly.apigen.generator;

import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Heiko Braun
 * @since 30/07/15
 */
public class MetaDataTraversal {

    private static final Logger log = Logger.getLogger(MetaDataTraversal.class.getName());

    private final LinkedList<ResourceMetaData> children;
    private final ResourceMetaData rootMetaData;

    public MetaDataTraversal(ResourceMetaData rootMetaData) {

        this.rootMetaData = rootMetaData;

        children = new LinkedList<>();

        dfs(rootMetaData.getAddress(), rootMetaData.getDescription(), (address, desc) -> {
            ResourceMetaData child = new ResourceMetaData(address, desc);
            child.setAllCfg(rootMetaData.getAllCfg()); // merge the assorted configuration values
            children.add(child);
        });

        Collections.sort(children);
    }

    private void dfs(AddressTemplate address, ResourceDescription root, Applicable applicable) {
        if (root == null) return;

        applicable.apply(address, root);

        if (root.hasChildrenDefined()) {

            // regular child resources
            Set<String> regularChildren = root.getChildrenTypes();
            for (String childType : regularChildren) {

                ResourceDescription childDescription = root.getChildDescription(childType, "*");
                AddressTemplate next = address.append(childType + "=*");
                dfs(next, childDescription, applicable);
            }

            // singleton child resources
            Set<String> singletonChildren = root.getSingletonChildrenTypes();
            for (String childType : singletonChildren) {
                String[] tokens = childType.split("=");
                ResourceDescription childDescription = root.getChildDescription(tokens[0], tokens[1]);
                childDescription.setSingletonName(tokens[1]);
                AddressTemplate next = address.append(tokens[0] + "=" + tokens[1]);
                dfs(next, childDescription, applicable);
            }

        }
    }

    public Iterator<ResourceMetaData> createInstance() {
        return children.iterator();
    }

    public interface Applicable {
        void apply(AddressTemplate address, ResourceDescription desc);
    }
}



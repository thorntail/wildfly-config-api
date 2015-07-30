package org.wildfly.apigen.generator;

import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 30/07/15
 */
public class MetaDataIterator {

    private final LinkedList<ResourceMetaData> children;
    private final ResourceMetaData rootMetaData;

    public MetaDataIterator(ResourceMetaData rootMetaData) {
        this.rootMetaData = rootMetaData;

        children = new LinkedList<>();

        dfs(rootMetaData.getAddress(), rootMetaData.getDescription(), new Applicable() {
            @Override
            public void apply(AddressTemplate address, ResourceDescription desc) {
                ResourceMetaData child = new ResourceMetaData(address, desc);
                child.setAll(rootMetaData.getAll()); // merge the assorted configuration values
                children.add(child);
            }
        });

        Collections.sort(children);
    }

    private void dfs(AddressTemplate addresss, ResourceDescription root, Applicable applicable) {
        if(root == null) return;

        applicable.apply(addresss, root);

        if(root.hasChildren())
        {
            Set<String> childrenNames = root.getChildrenNames();
            for (String childrenName : childrenNames) {
                dfs(addresss.append(childrenName+"=*"), root.getChildDescription(childrenName), applicable);
            }
        }

    }

    public Iterator<ResourceMetaData> createInstance() {
        return children.iterator();
    }


    interface Applicable {
        void apply(AddressTemplate address, ResourceDescription desc);
    }
}



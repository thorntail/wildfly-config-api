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

    private final AddressTemplate address;
    private final ResourceDescription desc;
    private final LinkedList<ResourceMetaData> children;

    public MetaDataIterator(ResourceMetaData rootMetaData) {
        this.address = rootMetaData.getAddress();
        this.desc = rootMetaData.getDescription();

        children = new LinkedList<>();

        dfs(address, desc, new Applicable() {
            @Override
            public void apply(AddressTemplate address, ResourceDescription desc) {
                children.add(new ResourceMetaData(address, desc));
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



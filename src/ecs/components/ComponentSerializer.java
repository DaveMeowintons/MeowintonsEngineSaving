package ecs.components;

import utils.serialisation.dataObjects.TSBase;

import java.util.List;

public interface ComponentSerializer {
    List<TSBase> serialise();
}

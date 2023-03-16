package _main;

import ecs.components.Component;
import ecs.components.ComponentSerializer;
import utils.serialisation.dataObjects.TSBase;
import utils.serialisation.dataObjects.TSField;

import java.util.ArrayList;
import java.util.List;

public class MeshComponent extends Component implements ComponentSerializer {
    private final String file;

    public MeshComponent(String file){
        this.file = file;
    }

    @Override
    public List<TSBase> serialise(){
        List<TSBase> fields = new ArrayList<>();
        fields.add(TSField.String("file", file));

        return fields;
    }
}

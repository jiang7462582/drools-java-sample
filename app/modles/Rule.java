package modles;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;


/**
 * Created by jiang on 16/7/14.
 */
public class Rule {

    public static  boolean isBrithday(Long time) {
        return true;
    }

    public static void printName(String name){
        System.out.println("你的名字是:" +name);
    }
}

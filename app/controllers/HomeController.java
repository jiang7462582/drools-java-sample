package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import modles.User;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.executor.STATUS;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private  static String RULE_PACKAGE = "userCostTest.drl";


//    private static List<String> getAllRules() {
//        URL dir = ClassLoader.getSystemClassLoader().getResource(RULE_PACKAGE);
//
//        System.out.println(dir);
//        List<String> rv = new LinkedList<>();
//        if(dir == null) {
//            return rv;
//        }
//
//        try {
//            File fDir = new File(dir.toURI());
//            File[] files = fDir.listFiles();
//            if (files != null) {
//                for(File file : files) {
//                    if(file.getName().endsWith(".drl")) {
//                        rv.add(file.getName());
//                    }
//                }
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return rv;
//    }



    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result userCost(){
        JsonNode rb = request().body().asJson();
        Long distance = rb.get("distance").asLong();
        Long waitTime = rb.get("waitTime").asLong();
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write(ResourceFactory.newClassPathResource(RULE_PACKAGE));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            // Do something with the error messages. Log them perhaps...
            throw new RuntimeException(kieBuilder.getResults().toString());
        }

        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userRuleCost", "自定义用户消费金额");
        User user = new User();
        user.setDistance(distance);
        user.setWaitTime(waitTime);
        kieSession.insert(user);
        kieSession.fireAllRules();
        return ok("用户最终消费: "+user.getCost());
    }

}

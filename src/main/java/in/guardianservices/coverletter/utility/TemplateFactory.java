package in.guardianservices.coverletter.utility;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TemplateFactory {

    private static VelocityEngine velocityEngine = null;

    static {
        velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        props.put("runtime.log.logsystem.log4j.category", "velocity");
        props.put("runtime.log.logsystem.log4j.logger", "velocity");

        props.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
        props.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        velocityEngine.init(props);
    }

    public Template fetchTemplate(String templatePath) {

        return velocityEngine.getTemplate(templatePath);
    }
}

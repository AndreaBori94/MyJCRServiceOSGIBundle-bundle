package it.cgm.osgi.srv.myjcr.impl;

import it.cgm.osgi.srv.myjcr.HelloService;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

/**
 * One implementation of the {@link HelloService}. Note that the repository is
 * injected, not retrieved.
 */
@Service
@Component(metatype = false)
public class HelloServiceImpl implements HelloService {

	@Reference
	private ResourceResolverFactory resolverFactory;
	
	private Session session;
	private ResourceResolver resourceResolver = null;

	public String test(String node_path) {
		try {
			resourceResolver = resolverFactory
					.getAdministrativeResourceResolver(null);
			session = resourceResolver.adaptTo(Session.class);
			Node root = session.getRootNode();
			root.addNode(node_path);
			
			Node content = root.getNode(node_path);
			Node custNode = content;
			
			custNode.setProperty("id", 3);
			custNode.setProperty("firstName", "Andrea");
			custNode.setProperty("lastName", "Bori");
			custNode.setProperty("address", "Via da qui");
			custNode.setProperty("desc", "asc hahah");
			session.save();
			session.logout();
			return "fatto " + node_path;
		} catch (Exception e) {
			return e.getMessage() +"cause: "+ e.getCause();
		}
	}

}

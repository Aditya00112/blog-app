//import io.cerbos.api.CerbosClient;
//import io.cerbos.api.requests.CheckRequest;
//import io.cerbos.api.responses.CheckResponse;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CerbosAccessControlService {
//	
//	CheckResult result=client.check(
//		    Principal.newInstance("john","employee")
//		        .withPolicyVersion("20210210")
//		        .withAttribute("department",stringValue("marketing"))
//		        .withAttribute("geography",stringValue("GB")),
//		    Resource.newInstance("leave_request","xx125")
//		        .withPolicyVersion("20210210")
//		        .withAttribute("department",stringValue("marketing"))
//		        .withAttribute("geography",stringValue("GB"))
//		        .withAttribute("owner",stringValue("john")),
//		    "view:public","approve");
//
//		if(result.isAllowed("approve")){ // returns true if `approve` action is allowed
//		    ...
//		}
//
//    private final CerbosClient cerbosClient;
//
//    public CerbosAccessControlService(CerbosClient cerbosClient) {
//        this.cerbosClient = cerbosClient;
//    }
//
//    public boolean checkAccess(String subject, String action, Map<String, Object> resource) {
//        CheckRequest request = new CheckRequest.Builder()
//            .subject(subject)
//            .action(action)
//            .resource(resource)
//            .build();
//
//        CheckResponse response = cerbosClient.check(request);
//        return response.isAllowed();
//    }
//}

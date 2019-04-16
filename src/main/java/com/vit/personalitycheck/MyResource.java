package com.vit.personalitycheck;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        String MY_APP_ID = "185327351829024";
        String MY_APP_SECRET = "1b34db004182a178be79385c56ba9b87";
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(UserDataPermissions.USER_POSTS);
        scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);
        scopeBuilder.addPermission(UserDataPermissions.USER_FRIENDS);
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_2);
        String loginDialogUrlString = client.getLoginDialogUrl(MY_APP_ID, "http://localhost:8080/PersonalityCheck/loggedin.html", scopeBuilder);
        return loginDialogUrlString;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{myresource1}")
    public String getIt1(@PathParam("myresource1") String CODE) throws IOException,NullPointerException, Exception{
        try{
        JSONArray array = new JSONArray();
        String MY_ACCESS_TOKEN = "";
        String MY_APP_ID = "185327351829024";
        String MY_APP_SECRET = "1b34db004182a178be79385c56ba9b87";
        FacebookClient facebookClient;
        AccessToken accessToken = new DefaultFacebookClient().obtainUserAccessToken(MY_APP_ID, MY_APP_SECRET, "http://localhost:8080/PersonalityCheck/loggedin.html", CODE);
        MY_ACCESS_TOKEN = accessToken.getAccessToken();
        System.out.println(MY_ACCESS_TOKEN);
        facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN, MY_APP_SECRET);
        User user = facebookClient.fetchObject("me", User.class);
        System.out.println("User name: " + user.getName());
        Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
        System.out.println("Count of my friends: " + myFriends.getData().size());
        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
        int ctr = 0;
        for (List<Post> myFeedConnectionPage : myFeed) {
            for (Post post : myFeedConnectionPage) {
                
                if (post.getMessage() != null) {
                    System.out.println("Post"+ctr+":" + post.getMessage());
                    String pos=post.getMessage();
                    JSONObject j = new JSONObject();
                    j.put("id",Integer.toString(ctr));
                    j.put("post",pos);
                    array.put(j); 
                    ctr++;
                }
            }
        }
        System.out.println("Created docs");
        return array.toString();
        }
        catch(Exception e)
        {
            return "false";
        }
    }
}

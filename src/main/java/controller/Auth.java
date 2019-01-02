package controller;

import capman.Capman;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * Shared class used by every sample. Contains methods for authorizing a user and caching credentials.
 */
public class Auth {

    public static final String APPLICATION_NAME = "Capman/1.0";
    
    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    
    public static final int LOCAL_SERVER_PORT = 31416;

    
    /** OAuth 2.0 & Youtube scopes. */
    private static final List<String> SCOPES = Arrays.asList(
      "https://www.googleapis.com/auth/userinfo.profile",
      "https://www.googleapis.com/auth/userinfo.email",
      "https://www.googleapis.com/auth/youtube.force-ssl");
    
    private static Oauth2 oauth2;
    
    /**
     * Authorizes the installed application to access user's protected data.
     * @param accountID name of the credential datastore to cache OAuth tokens
     */
    public static Credential authorize(String accountID) throws IOException {

        // Load client secrets.
        Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/client_secrets.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        // This creates the credentials datastore at ~/.capman-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(Capman.CREDENTIALS_DIRECTORY));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(accountID);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setCredentialDataStore(datastore)
                .build();

        // Build the local server and bind it to port 31416(PI)
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(LOCAL_SERVER_PORT).build();

        // Authorize.
        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }
    
    public static void removeCredentialFile(String accountID) {
        String path = Capman.CREDENTIALS_DIRECTORY + File.separator + accountID;
        File credentialFile = new File(path);
        credentialFile.delete();
    }
    
    public static void loadOauth(Credential credential){
        oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME).build();
    }
    
    public static String getOauthEmail() throws IOException {
        Userinfoplus userinfo = oauth2.userinfo().get().execute();
        return userinfo.getEmail();
    }
    
    public static void userInfo() throws IOException {
        Userinfoplus userinfo = oauth2.userinfo().get().execute();
        System.out.println(userinfo.toPrettyString());
    }
}
package csci.project.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private final FirebaseDatabase db;

    public Database() throws IOException {
        this.db = FirebaseDatabase.getInstance("https://csci-455-project-1-default-rtdb.firebaseio.com/");
    }

    public void test() {
        DatabaseReference ref = db.getReference("message");
        ref.setValue("hello world", (x, y) -> {});
    }

}

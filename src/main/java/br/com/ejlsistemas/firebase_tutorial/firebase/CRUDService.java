/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ejlsistemas.firebase_tutorial.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author estelson
 */
@Service
public class CRUDService {

    public String createCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionsApiFuture = getFirestore().collection("crud_user").document(crud.getDocumentId()).set(crud);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public CRUD getCRUD(String documentId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = getFirestore().collection("crud_user").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        // Converts document to Java object
        CRUD crud;
        if (document.exists()) {
            crud = document.toObject(CRUD.class);
            return crud;
        }

        return null;
    }

    public List<CRUD> getAllCRUDs() throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = getFirestore().collection("crud_user");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();

        List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

        return queryDocumentSnapshots.stream()
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(CRUD.class)).collect(Collectors.toList());
    }

    public String updateCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionsApiFuture = getFirestore().collection("crud_user").document(crud.getDocumentId()).set(crud);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteCRUD(String documentId) {
        ApiFuture<WriteResult> writeResult = getFirestore().collection("crud_user").document(documentId).delete();

        return "Successfully deleted " + documentId;
    }

    private Firestore getFirestore() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        return dbFirestore;
    }

}

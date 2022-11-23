/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ejlsistemas.firebase_tutorial.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

/**
 *
 * @author estelson
 */
@Service
public class CRUDService {
    
    public String createCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionsApiFuture = getFirestore().collection("crud_user").document(crud.getName()).set(crud);
        
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    
    public CRUD getCRUD(String documentId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = getFirestore().collection("crud_user").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        
        // Converts document to Java object
        CRUD crud;
        if(document.exists()) {
            crud = document.toObject(CRUD.class);
            return crud;
        }
        
        return null;
    }
    
    public String updateCRUD(CRUD crud) {
        return "";
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

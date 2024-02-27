package com.lisade.togeduck.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FcmConfig {

//    @Value("${fcm.service-account-file}")
//    private String firebaseConfig;
//    @Value("${fcm.project-id}")
//    private String firebaseProjectId;
//
//    @PostConstruct
//    public void initialize() {
//        try {
//            InputStream serviceAccount = new FileInputStream(firebaseConfig);
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setProjectId(firebaseProjectId)
//                .build();
//
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

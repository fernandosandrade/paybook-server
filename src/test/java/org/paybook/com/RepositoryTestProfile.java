package org.paybook.com;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class RepositoryTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("firebase.adminsdk.jsonPath",
                "src/main/resources/META-INF/resources/plink-2b309-firebase-adminsdk-m2nbr-c17700bc21.json");
    }
}

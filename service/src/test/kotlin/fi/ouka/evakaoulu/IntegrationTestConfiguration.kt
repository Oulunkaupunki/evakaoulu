// SPDX-FileCopyrightText: 2024 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu

import com.auth0.jwt.algorithms.Algorithm
import fi.espoo.evaka.BucketEnv
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey

@TestConfiguration
class IntegrationTestConfiguration {
    @Bean
    fun s3Client(bucketEnv: BucketEnv): S3Client {
        val client =
            S3Client.builder()
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .endpointOverride(bucketEnv.s3MockUrl)
                .credentialsProvider(
                    StaticCredentialsProvider.create(AwsBasicCredentials.create("foo", "bar")),
                )
                .build()

        val existingBuckets = client.listBuckets().buckets().map { it.name() }
        bucketEnv.allBuckets()
            .filterNot { bucket -> existingBuckets.contains(bucket) }
            .forEach { bucket -> client.createBucket { it.bucket(bucket) } }

        return client
    }

    @Bean
    fun s3Presigner(bucketEnv: BucketEnv): S3Presigner =
        S3Presigner.builder()
            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .endpointOverride(bucketEnv.s3MockUrl)
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create("foo", "bar")),
            )
            .build()

    @Bean
    fun jwtAlgorithm(): Algorithm {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048)
        val keyPair = generator.generateKeyPair()
        val jwtPublicKey = keyPair.public as RSAPublicKey
        return Algorithm.RSA256(jwtPublicKey, null)
    }
}

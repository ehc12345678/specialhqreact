package com.specialhq.rest.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration {
    @Primary
    @Bean(name = ["readWriteDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.read-write")
    fun readWriteDataSource(): DataSource = DataSourceBuilder.create().build()

    @Primary
    @Bean(name = [ENTITY_MANAGER_FACTORY])
    fun readWriteEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("readWriteDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        builder.dataSource(dataSource)
            .packages(LIBRARY_BASE_PACKAGE)
            .persistenceUnit(PERSISTENCE_UNIT)
            .build()

    /**
     * Transaction management bean
     */
    @Primary
    @Bean(name = [TRANSACTION_MANAGER])
    fun readWriteOmtTransactionManager(
        @Qualifier(value = ENTITY_MANAGER_FACTORY) emf: EntityManagerFactory
    ): PlatformTransactionManager =
        JpaTransactionManager(emf)

    companion object {
        // Matches Ping PU for easier integration; Ideally would like make this unique to ping at some point
        const val PERSISTENCE_UNIT = "specialHq_PU"
        const val ENTITY_MANAGER_FACTORY = "readWriteEntityManagerFactory"
        const val TRANSACTION_MANAGER = "readWriteTransactionManager"
        const val LIBRARY_BASE_PACKAGE = "com.specialhq.lib"

        /**
         * bean name for task executor to avoid future bean conflicts
         */
        const val TASK_EXECUTOR = "taskExecutor"

        private const val QUEUE_CAPACITY = 500
    }

}
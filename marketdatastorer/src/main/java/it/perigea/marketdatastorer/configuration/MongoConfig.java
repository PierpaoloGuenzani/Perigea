package it.perigea.marketdatastorer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * A class to that extend AbstractMongoClientConfiguration for configure MongoDB database
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration
{
	@Value("${spring.data.mongodb.uri}")
	private String mongoServer;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	

	/**
	 * A function for getting the database name
	 */
	@Override
	protected String getDatabaseName() 
	{
		return database;
	}
	
	/**
	 * A function for creating a Spring Bean that connect to a MongoDB server
	 * @return {@link com.mongodb.client.MongoClient} - a new client for ours MondoDB server 
	 */
	@Bean
	@Override
	public MongoClient mongoClient()
	{
		ConnectionString connectionString = new ConnectionString(mongoServer);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        
        return MongoClients.create(mongoClientSettings);
	}
	
	@Override
	public boolean autoIndexCreation()
	{
		return true;
	}

}

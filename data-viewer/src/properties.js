export const properties =
{
    DATA_IMPORTER_SCHEDULER_URI: "http://localhost:8080/scheduler/",
    DATA_STORER_URI: "http://localhost:8081/storage/",
    DATA_IMPORTER_DAILY_URI: "http://localhost:8080/forexDaily/",
    DATA_STORER_EXCHANGE_RATE: "http://localhost:8081/storage/exchange-rates/",
    DATA_STORER_HISTORICAL: "http://localhost:8081/storage/exchange-rates/historical/",
    LOGIN_URL: "http://localhost:8082/",
    CREATE_USER: "http://localhost:8082/create",

    setDocker: function()
    {
        if(process.env.NODE_ENV == "production")
        {
            properties.DATA_IMPORTER_SCHEDULER_URI = "http://market-data-importer:8080/scheduler/";
            properties.DATA_STORER_URI = "http://market-data-storer:8081/storage/";
            properties.DATA_IMPORTER_DAILY_URI = "http://market-data-importer:8080/forexDaily/";
            properties.DATA_STORER_EXCHANGE_RATE = "http://market-data-storer:8081/storage/exchange-rates/";
            properties.DATA_STORER_HISTORICAL = "http://market-data-storer:8081/storage/exchange-rates/historical/";
            properties.LOGIN_URL = "http://market-data-login:8082/";
            properties.CREATE_USER = "http://market-data-login:8082/create";
        }
        console.log(properties);
    }
};
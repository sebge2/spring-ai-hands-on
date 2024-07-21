# Spring AI Hands On

## Setup

First of all, starts Postgresql Server:

````
docker run \
    -it \
    -v postgresml_data:/var/lib/postgresql \
    -p 5432:5432 \
    -p 8000:8000 \
    ghcr.io/postgresml/postgresml:2.7.12 \
    sudo -u postgresml psql -d postgresml
````

Then start the application. Please specify your API keys in your environment variables:
````
MISTRAL_AI_API_KEY=XXXX;
OPEN_API_API_KEY=XXXX
````


## How to

1. Go on the [Swagger](http://localhost:8082/api/swagger-ui.html) application. 
1. Upload documents. The project brings some [samples](./samples).
1. Start to prompt by text or by speech. You can prompt on your documents (RAG), or ask general questions.


## Links

* [Spring AI Example](https://medium.com/@akash4chandran/enhancing-conversational-ai-with-openai-and-spring-ai-using-pg-vector-store-for-advanced-embedding-b1c9e733eca2)
* [Vector Database and Spring AI](https://dev.to/lucasnscr/vector-database-and-spring-ia-4dll)



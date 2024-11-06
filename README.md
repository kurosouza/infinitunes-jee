# infinitunes

## Description

Infinitunes is a lyrics-based music search application built using Jakarta EE 10 on Payara Micro 6. It has been built as a fun application to demonstrate the use of AI and Large Language Models (LLMs) on the Payara Server Ecosystem.

It helps you find songs not by song title or artist but by the lyrics of the songs. LLMs help in this approach as they are able to detect some context from the song lyrics and enable the user to search based on this deeper level information.

Infinitune has also been designed to let you search using human-friendly simple language queries and it shows results based on how well the lyrics of the songs match the search query.

See it running here: http://infinitunefe.codegarage.cloud/

## Design
Infinitunes is made up of three components
- msd-utilities https://github.com/kurosouza/msd-utilities
- infinitunes-api (this repo)
- infinitunes-frontend https://github.com/kurosouza/infinitunes-frontend

Infinitunes at its core is driven by an in-app LLM that is a trained on the MusixMatch lyrics database[2]. This database is derived from the MillionSong Dataset[1] and is enhanced by adding keywords from the lyrics of the songs in the Million Song Dataset.

The song lyrics data is vectorized using the Jina AI V2 Embeddings Model[3] and exported to JSON using the *msd-utilities* functions. The ms-utilities program uses Langchain4j to load the Embeddings model in the ONNX format and processes the song lyrics data before export. The exported JSON data was then imported into Datastax Astra[4] as a vector datastore.

## Components

#### msd-utilities
This is the data processing and ingestion tool. It has been used to extract the data from the Musixmatch dataset. To use it, you need to download the musixmatch dataset (from the link in the appendix) and set the paths in the code at `MUSIXMATCH_FILE_PATH` in SongUtils.java to this path. When you run the Main.java file it should extract the songs data from the data set and create a `songs.json` file in the project root directory. This file can then be imported into Astra or any other Vector Datastore.

#### infinitunes-api
This is the backend API that holds the song query REST endpoint. It is a Jakarta EE 10 application and uses the Payara Micro web container. It contains the model.onnx and tokenizer.json files for the Embeddings model. You can also download the model.onnx from the link in the appendix as I have removed it due to its size. 

You can build this package by running:
``` mvn clean package ```
You will need a JDK >=17 and Maven installed to run this.

** Please note that the larger files have been excluded from the repository because of the file size **

#### infinitunes-frontend
I have added a frontend application built using React / NextJS that provides a UI for the application and queries the Payara Micro service. You can start it by running:
```sh
$ npm install
$ npm run dev
```
The fronted app should then be started on port 3000

## How to Run
To run the infinitunes-api in Payara, download the Payara Micro JAR file and then build the infinitunes-api as described in the section above (`mvn clean package`)

Next start the server using the war file by running:
```sh
java -jar ../../payara-micro-6.2024.9.jar --deploy ./target/infinitune.war --nohazelcast --contextroot / --port 9080
```
This should start the Payara Micro server with the api enabled on port 9080. You can then query the server by using curl or httpie:
```
http localhost:9080/infinitunes/infinitunes/songs q=='famous love songs'
```
The REST API and Frontend applications have been deployed to a Dokku instance and should is available at: [http://infinitunefe.codegarage.cloud](http://infinitunefe.codegarage.cloud)

## Important Files

Datastax Astra Vector Datastore
Langchain4j
ONNX Format

## Appendix
1. Million Song Dataset http://millionsongdataset.com/ 
2. Musixmatch Dataset http://millionsongdataset.com/musixmatch/
3. Jina AI V2 Embeddings Model on HuggingFace https://huggingface.co/jinaai/jina-embeddings-v2-small-en
4. Datastax Astra https://astra.datastax.com/
5. Langchain4j https://docs.langchain4j.dev/

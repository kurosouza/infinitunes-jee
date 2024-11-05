# infinitunes

## Description
Infinitunes is a lyrics-based music search application built using Jakarta EE 10 on Payara Micro 6. It has been built as a fun application to demonstrate the use of AI and Large Language Models (LLMs) on the Payara Server Ecosystem.

See it running here: http://infinitunefe.codegarage.cloud/

# Design
Infinitunes is made up of three components
- msd-utilities https://github.com/kurosouza/msd-utilities
- infinitunes-api (this repo)
- infinitunes-frontend https://github.com/kurosouza/infinitunes-frontend

Infinitunes at its core is driven by an in-app LLM that is a trained on the MusixMatch lyrics database[2]. This database is derived from the MillionSong Dataset[1] and is enhanced by adding keywords from the lyrics of the songs in the Million Song Dataset.

The song lyrics data is vectorized using the Jina AI V2 Embeddings Model[3] and exported to JSON using the *msd-utilities* functions. The ms-utilities program uses Langchain4j to load the Embeddings model in the ONNX format and processes the song lyrics data before export. The exported JSON data was then imported into Datastax Astra[4] as a vector datastore.

## How to Run

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

# literate-pancake
Just a little Micronaut project do demonstrate an eror case in the reactiverse reactive-pg-client.

See [this issue](https://github.com/reactiverse/reactive-pg-client/issues/270).

The base project was created by running:

`mn create-app -i --lang=java --build=maven --features=java,junit,postgres-reactive agschaid.reactivepg.preparedq`

## DB Setup

### start db
docker run --name contracts-postgres -rm -p 5432:5432 -e POSTGRES_PASSWORD=discoStuLovesDisco -d postgres

### set up schema
psql -U postgres -d postgres -h localhost -a -f src/main/resources/schema.sql
package agschaid.reactivepg.storage;

import agschaid.reactivepg.dtos.Contract;
import io.reactiverse.reactivex.pgclient.PgPool;
import io.reactiverse.reactivex.pgclient.Tuple;
import io.reactiverse.reactivex.pgclient.data.Json;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class ContractRepository
{
    private final PgPool pool;

    public ContractRepository(PgPool pool) {
        this.pool = pool;
    }

    private static final String CREATE_QUERY = "INSERT INTO contracts (json) VALUES ($1);";
    private static final String CREATE_QUERY_NOT_PREPARED = "INSERT INTO contracts (json) VALUES ('%s');";

    public Single<UUID> storeNewContractPreparedWithString(Contract contract) {

        UUID uuid = contract.getId();

        return Single.just(contract)
            .map(JsonObject::mapFrom)
            .map(Json::create)
            .map(a -> a.toString())
            .flatMap( foo -> pool.rxPreparedQuery(CREATE_QUERY, Tuple.of(foo)) )
            .map( ar -> uuid);
    }

    public Single<UUID> storeNewContractPrepared(Contract contract) {

        UUID uuid = contract.getId();

        return Single.just(contract)
            .map(JsonObject::mapFrom)
            .map(Json::create)
            .flatMap( foo -> pool.rxPreparedQuery(CREATE_QUERY, Tuple.of(foo)) )
            .map( ar -> uuid);
    }

    public Single<UUID> storeNewContractNotPrepared(Contract contract) {

        UUID uuid = contract.getId();

        return Single.just(contract)
            .map(JsonObject::mapFrom)
            .map(Json::create)
            .flatMap(foo -> pool.rxQuery(String.format(CREATE_QUERY_NOT_PREPARED, foo)))
            .map(ar -> uuid);
    }
}

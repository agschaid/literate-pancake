DROP INDEX IF EXISTS contracts_ids_idx;
DROP TABLE IF EXISTS contracts;

CREATE TABLE contracts (
    json JSONB NOT NULL,
    CONSTRAINT validate_id CHECK( json ? 'id' )
);

CREATE UNIQUE INDEX contracts_ids_idx ON contracts( (json ->> 'id') );
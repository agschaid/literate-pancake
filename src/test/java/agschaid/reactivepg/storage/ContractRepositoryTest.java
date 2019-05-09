package agschaid.reactivepg.storage;

import agschaid.reactivepg.dtos.Contract;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import io.reactiverse.pgclient.PgException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class ContractRepositoryTest
{

    private Random random = new Random();
    public ContractRepository repo;

    private static EmbeddedServer server;

    @BeforeAll
    public static void setupServer()
    {
        server = ApplicationContext.run(EmbeddedServer.class);

    }

    @AfterAll
    public static void stopServer()
    {
        if (server != null)
        {
            server.stop();
        }
    }

    @BeforeEach
    public void setUp()
    {
        repo = server.getApplicationContext().getBean(ContractRepository.class);

    }

    @Test
    public void testUnpreparedStatement()
    {
        Contract contract = createRandomContract();

        UUID id = repo.storeNewContractNotPrepared(contract)
            .blockingGet();

        Assertions.assertNotNull(id);
        Assertions.assertEquals(contract.getId(), id);

    }

    @Test
    public void testPreparedStatement()
    {
        Contract contract = createRandomContract();

        // this is the one we are baffled with. Note that it uses the same way to transform the object to a JSON obhject
        // like the not-prepared version in the test above (which works).
        Assertions
            .assertThrows(RuntimeException.class, () -> repo.storeNewContractPrepared(contract).blockingGet(),
                "Values [null] cannot be coerced to [Json]");
    }

    @Test
    public void testPreparedStatementWithString()
    {
        // OK actually we figured this out. By transforming it into a String first it tries to write a #drumRoll String
        // which of course doesn't have an id.
        Contract contract = createRandomContract();

        repo.storeNewContractPreparedWithString(contract).blockingGet();
        Assertions
            .assertThrows(PgException.class, () -> repo.storeNewContractPreparedWithString(contract).blockingGet(),
                "new row for relation \"contracts\" violates check constraint \"validate_id\"");

    }

    private Contract createRandomContract()
    {
        Contract c = new Contract();

        c.setId(UUID.randomUUID());

        c.setZeMoney(random.nextInt());
        c.setZeText(String.format("random%n", random.nextInt()));

        return c;

    }
}

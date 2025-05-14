package base;

import client.auth.AuthClient;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest {

    protected String token;

    @BeforeEach
    public void authorize() {
        token = new AuthClient().loginDefault();
        System.out.println("Bearer token: " + token);
    }
}

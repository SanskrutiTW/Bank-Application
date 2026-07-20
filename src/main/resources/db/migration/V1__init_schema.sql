CREATE TABLE customers
(
    id            BIGSERIAL PRIMARY KEY,
    customer_uuid UUID DEFAULT gen_random_uuid() ,
    first_name    VARCHAR(100)                   NOT NULL,
    last_name     VARCHAR(100)                   NOT NULL,
    email         VARCHAR(255)                   NOT NULL,
    phone_number  VARCHAR(15)                    NOT NULL,
    address       VARCHAR(255)                   NOT NULL,


    CONSTRAINT uq_email UNIQUE (email),
    CONSTRAINT uq_phone_number UNIQUE (phone_number)

);

CREATE TABLE accounts
(
    id                BIGSERIAL PRIMARY KEY,
    account_number    VARCHAR(20)                         NOT NULL,
    customer_id       BIGINT                              NOT NULL,
    account_type      VARCHAR(20)                         NOT NULL,
    account_uuid      UUID  DEFAULT gen_random_uuid()    ,
    balance           NUMERIC(15, 2)                      NOT NULL,
    minimum_balance   NUMERIC(15, 2)                      NOT NULL,
    status            VARCHAR(20) DEFAULT 'ACTIVE'        NOT NULL,
    ifsc_code         VARCHAR(11)                         NOT NULL,
    branch            VARCHAR(100)                        NOT NULL,

    created_at        TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    account_opened_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,

    CONSTRAINT uq_account_number UNIQUE (account_number),
    CONSTRAINT uq_account_uuid UNIQUE (account_uuid),
    CONSTRAINT fk_accounts_customer FOREIGN KEY (customer_id)
        REFERENCES customers (id)
        ON DELETE RESTRICT
);
CREATE INDEX idx_accounts_customer_id ON accounts (customer_id);


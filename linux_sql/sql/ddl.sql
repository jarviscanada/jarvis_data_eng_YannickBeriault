CREATE TABLE PUBLIC.host_info
(
    id                  SERIAL NOT NULL,
    hostname            VARCHAR NOT NULL,
    cpu_number          INT,
    cpu_architecture    VARCHAR(50),
    cpu_model           VARCHAR(100),
    cpu_mhz             FLOAT,
    L2_cache            INT,
    total_mem           INT,
    timestamp           TIMESTAMP
);

CREATE TABLE PUBLIC.host_usage
(
    timestamp           TIMESTAMP NOT NULL,
    host_id             SERIAL NOT NULL,
    memory_free         INT,
    cpu_idle            SMALLINT,
    cpu_kernel          SMALLINT,
    disk_io             INT,
    disk_available      INT
);
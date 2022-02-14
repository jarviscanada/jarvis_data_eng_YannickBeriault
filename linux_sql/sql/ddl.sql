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

#The following views are meant to be used by queries
CREATE VIEW groupedDates AS
SELECT host_id, date_trunc('hour', timestamp) + date_part('minute', timestamp):: \
        int / 5 * interval '5 min' AS groupedDate
FROM host_usage;

CREATE VIEW memUsed AS
SELECT hU.host_id, hI.hostname, (((total_mem - memory_free) * 100) / total_mem) AS avg_used_mem_percentage
FROM host_usage AS hU JOIN host_info AS hI
ON hU.host_id = hI.id;
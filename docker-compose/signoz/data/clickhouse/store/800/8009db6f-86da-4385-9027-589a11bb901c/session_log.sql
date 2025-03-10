ATTACH TABLE _ UUID 'fdbbb4b0-8f69-4f90-a9c4-c15886319173'
(
    `type` Enum8('LoginFailure' = 0, 'LoginSuccess' = 1, 'Logout' = 2),
    `auth_id` UUID,
    `session_id` String,
    `event_date` Date,
    `event_time` DateTime,
    `event_time_microseconds` DateTime64(6),
    `user` String,
    `auth_type` Enum8('NO_PASSWORD' = 0, 'PLAINTEXT_PASSWORD' = 1, 'SHA256_PASSWORD' = 2, 'DOUBLE_SHA1_PASSWORD' = 3, 'LDAP' = 4, 'KERBEROS' = 5),
    `profiles` Array(LowCardinality(String)),
    `roles` Array(LowCardinality(String)),
    `settings` Array(Tuple(LowCardinality(String), String)),
    `client_address` IPv6,
    `client_port` UInt16,
    `interface` Enum8('TCP' = 1, 'HTTP' = 2, 'gRPC' = 3, 'MySQL' = 4, 'PostgreSQL' = 5),
    `client_hostname` String,
    `client_name` String,
    `client_revision` UInt32,
    `client_version_major` UInt32,
    `client_version_minor` UInt32,
    `client_version_patch` UInt32,
    `failure_reason` String
)
ENGINE = MergeTree
PARTITION BY toYYYYMM(event_date)
ORDER BY (event_date, event_time)
SETTINGS index_granularity = 8192

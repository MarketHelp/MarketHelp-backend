--liquibase formatted sql
--changeset ovrays:001-Initialize-schema

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE user_role AS ENUM ('user', 'admin');

CREATE TABLE "user"
(
    id         UUID PRIMARY KEY,
    login      TEXT      NOT NULL,
    role       user_role NOT NULL,
    updated_at TIMESTAMP NOT NULl,
    created_at TIMESTAMP NOT NULL,

    UNIQUE (login)
);

CREATE TABLE user_auth
(
    user_id    UUID PRIMARY KEY REFERENCES "user" (id),
    password   TEXT      NOT NULL,
    updated_at TIMESTAMP NOT NULl,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE shop
(
    id         INT       NOT NULL,
    user_id    UUID      NOT NULL REFERENCES "user" (id),
    name       TEXT      NOT NULL,
    updated_at TIMESTAMP NOT NULl,
    created_at TIMESTAMP NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (user_id, name)
);

CREATE TABLE api_key
(
    shop_id     INT PRIMARY KEY REFERENCES shop (id),
    value       TEXT      NOT NULL,
    valid_until TIMESTAMP NOT NULL,
    created_at  TIMESTAMP NOT NULl
);

CREATE TABLE product
(
    id         UUID PRIMARY KEY,
    shop_id    INT      NOT NULL REFERENCES shop (id),
    name       TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULl
);

CREATE TABLE product_visualization
(
    id         UUID PRIMARY KEY,
    product_id UUID REFERENCES product (id),
    format     TEXT      NOT NULL,
    filepath   TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULl
);
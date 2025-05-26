--liquibase formatted sql
--changeset ovrays:002-Add-visualization-idx

CREATE UNIQUE INDEX ON product_visualization (product_id, filepath, format)

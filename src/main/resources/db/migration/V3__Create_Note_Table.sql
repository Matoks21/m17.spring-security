-- V3__Create_Note_Table.sql

CREATE TABLE note (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL

);

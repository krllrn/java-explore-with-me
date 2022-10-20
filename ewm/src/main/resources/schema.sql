DROP TABLE IF EXISTS users, categories, compilations, events, compilations_events, requests;

CREATE TABLE IF NOT EXISTS users (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	email VARCHAR(128),
	name VARCHAR(128),
	CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name VARCHAR(128),
	CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	events BIGINT ARRAY,
	pinned BOOLEAN,
	title VARCHAR(128),
	CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	annotation VARCHAR(512),
	category_id BIGINT REFERENCES categories (id) ON DELETE CASCADE,
	created_on TIMESTAMP WITHOUT TIME ZONE,
	description TEXT,
	event_date TIMESTAMP WITHOUT TIME ZONE,
	initiator BIGINT REFERENCES users (id) ON DELETE CASCADE,
    location TEXT,
    paid VARCHAR(1),
    participant_limit INT,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation VARCHAR(1),
    state VARCHAR(20),
    title VARCHAR(128),
    views BIGINT,
    compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE,
	CONSTRAINT pk_events PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events (
	compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE,
	events_id BIGINT REFERENCES events (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS requests (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	created TIMESTAMP WITHOUT TIME ZONE,
	event_id BIGINT REFERENCES events (id) ON DELETE CASCADE,
	requester BIGINT REFERENCES users (id) ON DELETE CASCADE,
	status VARCHAR(20),
	CONSTRAINT pk_requests PRIMARY KEY (id)
);
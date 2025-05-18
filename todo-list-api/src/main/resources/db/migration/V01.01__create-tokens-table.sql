create table auth_tokens (
    username varchar(50) not null primary key,
    token varchar(500) not null,
    created_at timestamp not null,
    expires_at timestamp not null,
    constraint fk_auth_tokens_users foreign key(username) references users(username)
);

create unique index ix_auth_tokens_token on auth_tokens (token);